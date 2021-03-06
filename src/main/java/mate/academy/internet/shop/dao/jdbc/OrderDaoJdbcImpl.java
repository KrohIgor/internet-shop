package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
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
        String query = "SELECT * FROM orders WHERE order_id = ?";
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
        String query = "SELECT * FROM orders";
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
        String query = "DELETE FROM orders_products WHERE order_id = ?";
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
        String queryProducts = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statementOrderProducts =
                    connection.prepareStatement(queryProducts);
            statementOrderProducts.setLong(1, id);
            statementOrderProducts.executeUpdate();
            String queryOrders = "DELETE FROM orders WHERE order_id = ?";
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
        String query = "SELECT products.product_id, product_name, product_price "
                + "FROM orders_products INNER JOIN products "
                + "ON orders_products.product_id = products.product_id "
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, orderId);
            ResultSet resultSetProduct = preparedStatement.executeQuery();
            getOrderProducts(productList, resultSetProduct);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Order with id - " + orderId, e);
        }
        order.setProducts(productList);
        return order;
    }

    private void getOrderProducts(List<Product> productList, ResultSet resultSetProduct)
            throws SQLException {
        while (resultSetProduct.next()) {
            long productId = resultSetProduct.getLong("product_id");
            String productName = resultSetProduct.getString("product_name");
            Double productPrice = resultSetProduct.getDouble("product_price");
            Product product = new Product(productName, productPrice);
            product.setProductId(productId);
            productList.add(product);
        }
    }

    private void insertProductsToOrdersProducts(Order order, Connection connection)
            throws SQLException {
        String queryAddProduct = "INSERT INTO orders_products values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(queryAddProduct);
        for (Product product : order.getProducts()) {
            statement.setLong(1, order.getOrderId());
            statement.setLong(2, product.getProductId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Orders", e);
        }
        return orderList;
    }
}
