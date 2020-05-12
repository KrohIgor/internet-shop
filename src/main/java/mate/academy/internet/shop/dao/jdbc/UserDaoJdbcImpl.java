package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.UserDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Long ROLE_ID_USER = 1L;

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get User with login - " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO `internet-shop`.`users` "
                + "(`name`, `login`, `password`) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);
                user.setUserId(userId);
            }
            String queryRole = "INSERT INTO `internet-shop`.`users_roles` "
                    + "(`user_id`, `role_id`) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryRole);
            statement.setLong(1, user.getUserId());
            statement.setLong(2, ROLE_ID_USER);
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create User", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get User with id - " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Users", e);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE `internet-shop`.`users` SET `name` = ?,"
                + "`login` = ?, `password` = ? WHERE (`user_id` = ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get User with id - " + user.getUserId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM `internet-shop`.`users` WHERE (`user_id` = ?);";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete User with id - " + id, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, login, password);
        user.setUserId(userId);
        return user;
    }
}
