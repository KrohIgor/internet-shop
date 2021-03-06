package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long shoppingCartId = generatedKeys.getLong(1);
                shoppingCart.setShoppingCartId(shoppingCartId);
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create Shopping Cart", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCartFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Shopping Cart with id - " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts";
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingCartList.add(getShoppingCartFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Shopping Carts", e);
        }
        return shoppingCartList;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String query = "DELETE FROM shopping_carts_products WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getShoppingCartId());
            preparedStatement.executeUpdate();
            String queryUpdate = "INSERT INTO shopping_carts_products values(?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryUpdate);
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getShoppingCartId());
                statement.setLong(2, product.getProductId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update Shopping Cart with id - "
                    + shoppingCart.getShoppingCartId(), e);
        }
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        String queryProducts = "DELETE FROM shopping_carts_products WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatementShoppingCartProducts =
                    connection.prepareStatement(queryProducts);
            preparedStatementShoppingCartProducts.setLong(1, id);
            preparedStatementShoppingCartProducts.executeUpdate();
            String queryShoppingCarts = "DELETE FROM shopping_carts WHERE shopping_cart_id = ?";
            PreparedStatement preparedStatementShoppingCarts =
                    connection.prepareStatement(queryShoppingCarts);
            preparedStatementShoppingCarts.setLong(1, id);
            preparedStatementShoppingCarts.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete Shopping Cart with id - " + id, e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        long shoppingCartId = resultSet.getLong("shopping_cart_id");
        long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setShoppingCartId(shoppingCartId);
        List<Product> productList = new ArrayList<>();
        String query = "SELECT products.product_id, product_name, product_price "
                + "FROM shopping_carts_products INNER JOIN products "
                + "ON shopping_carts_products.product_id = products.product_id "
                + "WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCartId);
            ResultSet resultSetProduct = preparedStatement.executeQuery();
            getOrderProducts(productList, resultSetProduct);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Shopping Cart with id - "
                    + shoppingCartId, e);
        }
        shoppingCart.setProducts(productList);
        return shoppingCart;
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
}
