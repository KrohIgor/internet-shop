package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.dao.UserDao;
import mate.academy.internet.shop.exception.DataProcessingException;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.model.Role;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM `internet-shop`.users \n"
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id\n"
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id\n"
                + "WHERE users.login = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find User with login - " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO `internet-shop`.`users` "
                + "(`name`, `login`, `password`) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatementUser = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementUser.setString(1, user.getName());
            preparedStatementUser.setString(2, user.getLogin());
            preparedStatementUser.setString(3, user.getPassword());
            preparedStatementUser.executeUpdate();
            ResultSet generatedKeys = preparedStatementUser.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);
                user.setUserId(userId);
            }
            for (Role role : user.getRoles()) {
                String queryRole = "SELECT role_id FROM `internet-shop`.roles\n"
                        + "WHERE role_name = ?";
                PreparedStatement preparedStatementRole = connection.prepareStatement(queryRole);
                preparedStatementRole.setString(1, role.getRoleName().toString());
                ResultSet resultSet = preparedStatementRole.executeQuery();
                if (resultSet.next()) {
                    long roleId = resultSet.getLong("role_id");
                    String queryUserRole = "INSERT INTO `internet-shop`.`users_roles` "
                            + "(`user_id`, `role_id`) VALUES (?, ?)";
                    PreparedStatement preparedStatementUserRole =
                            connection.prepareStatement(queryUserRole);
                    preparedStatementUserRole.setLong(1, user.getUserId());
                    preparedStatementUserRole.setLong(2, roleId);
                    preparedStatementUserRole.executeUpdate();
                }
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create User", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM `internet-shop`.users \n"
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id\n"
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id\n"
                + "WHERE users.user_id = ?";
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
        String query = "SELECT * FROM `internet-shop`.users \n"
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id\n"
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id";
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update User with id - "
                    + user.getUserId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String queryUsersRoles = "DELETE FROM `internet-shop`.users_roles "
                + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement statementUsersRoles = connection.prepareStatement(queryUsersRoles);
            statementUsersRoles.setLong(1, id);
            statementUsersRoles.executeUpdate();
            String queryUsers = "DELETE FROM `internet-shop`.`users` WHERE user_id = ?";
            PreparedStatement statementUsers = connection.prepareStatement(queryUsers);
            statementUsers.setLong(1, id);
            statementUsers.executeUpdate();
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
        String roleName = resultSet.getString("role_name");
        User user = new User(name, login, password);
        user.setUserId(userId);
        user.setRoles(Set.of(Role.of(roleName)));
        return user;
    }
}
