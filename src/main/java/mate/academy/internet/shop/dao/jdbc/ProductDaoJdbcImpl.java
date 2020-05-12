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
        String query = "INSERT INTO `internet-shop`.`products` "
                + "(`productname`, `productprice`) VALUES (?, ?)";
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
            throw new DataProcessingException(e);
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
            throw new DataProcessingException(e);
        }
        return productList;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE `internet-shop`.`products` SET `productname` = ?, "
                + "`productprice` = ? WHERE (`product_id` = ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM `internet-shop`.`products` WHERE (`product_id` = ?);";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException(e);
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
