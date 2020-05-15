package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.ProductDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (productname, productprice) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long productId = generatedKeys.getLong(1);
                product.setProductId(productId);
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create Product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get Product with id - " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Products", e);
        }
        return productList;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET productname = ?, "
                + "productprice = ? WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update Product", e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String queryShoppingCartProduct = "DELETE FROM shopping_carts_products "
                + "WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatementShoppingCartProduct =
                    connection.prepareStatement(queryShoppingCartProduct);
            preparedStatementShoppingCartProduct.setLong(1, id);
            preparedStatementShoppingCartProduct.executeUpdate();
            String queryOrdersProducts = "DELETE FROM orders_products "
                    + "WHERE product_id = ?";
            PreparedStatement preparedStatementOrdersProducts =
                    connection.prepareStatement(queryOrdersProducts);
            preparedStatementOrdersProducts.setLong(1, id);
            preparedStatementOrdersProducts.executeUpdate();
            String queryProduct = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement preparedStatementProduct = connection.prepareStatement(queryProduct);
            preparedStatementProduct.setLong(1, id);
            preparedStatementProduct.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete Product with id - " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("productname");
        Double productPrice = resultSet.getDouble("productprice");
        Product product = new Product(productName, productPrice);
        product.setProductId(productId);
        return product;
    }
}
