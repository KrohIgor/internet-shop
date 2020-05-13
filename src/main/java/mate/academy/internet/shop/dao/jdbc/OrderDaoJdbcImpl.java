package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.dao.ProductDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {

    @Inject
    private ProductDao productDao;

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO `internet-shop`.`orders` (`user_id`) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long orderId = generatedKeys.getLong(1);
                order.setOrderId(orderId);
            }
            insertProductsToOrdersProducts(order, connection);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create Order", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM `internet-shop`.orders\n"
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Order with id - " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM `internet-shop`.orders";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Orders", e);
        }
        return orderList;
    }

    @Override
    public Order update(Order order) {
        String query = "DELETE FROM `internet-shop`.orders_products\n"
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getOrderId());
            preparedStatement.executeUpdate();
            insertProductsToOrdersProducts(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update Order with id - "
                    + order.getOrderId(), e);
        }
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String queryProducts = "DELETE FROM `internet-shop`.orders_products "
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statementOrderProducts =
                    connection.prepareStatement(queryProducts);
            statementOrderProducts.setLong(1, id);
            statementOrderProducts.executeUpdate();
            String queryOrders = "DELETE FROM `internet-shop`.`orders` "
                    + "WHERE order_id = ?";
            PreparedStatement statementShoppingCarts = connection.prepareStatement(queryOrders);
            statementShoppingCarts.setLong(1, id);
            statementShoppingCarts.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete Order with id - " + id, e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long orderId = resultSet.getLong("order_id");
        long userId = resultSet.getLong("user_id");
        Order order = new Order(userId);
        order.setOrderId(orderId);
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM `internet-shop`.orders_products\n"
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, orderId);
            ResultSet resultSetProduct = preparedStatement.executeQuery();
            while (resultSetProduct.next()) {
                Long productId = resultSetProduct.getLong("product_id");
                Product product = productDao.get(productId)
                        .orElseThrow(() ->
                                new DataProcessingException("Couldn't get Product with id - "
                                        + productId));
                productList.add(product);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Order with id - " + orderId, e);
        }
        order.setProducts(productList);
        return order;
    }

    private void insertProductsToOrdersProducts(Order order, Connection connection)
            throws SQLException {
        for (Product product : order.getProducts()) {
            String queryAddProduct = "INSERT INTO "
                    + "`internet-shop`.orders_products values(?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryAddProduct);
            statement.setLong(1, order.getOrderId());
            statement.setLong(2, product.getProductId());
            statement.executeUpdate();
        }
    }
}
