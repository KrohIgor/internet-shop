package mate.academy.internet.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
        String query = "SELECT * FROM users WHERE users.login = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(connection, resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find User with login - " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password, salt) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement preparedStatementUser = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementUser.setString(1, user.getName());
            preparedStatementUser.setString(2, user.getLogin());
            preparedStatementUser.setString(3, user.getPassword());
            preparedStatementUser.setBytes(4, user.getSalt());
            preparedStatementUser.executeUpdate();
            ResultSet generatedKeys = preparedStatementUser.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);
                user.setUserId(userId);
            }
            insertRoleUserToDB(user, connection);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create User", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE users.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(connection, resultSet));
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
                userList.add(getUserFromResultSet(connection, resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all Users", e);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, login = ?, password = ? WHERE user_id = ?";
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
        String queryUsersRoles = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement statementUsersRoles = connection.prepareStatement(queryUsersRoles);
            statementUsersRoles.setLong(1, id);
            statementUsersRoles.executeUpdate();
            String queryUsers = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement statementUsers = connection.prepareStatement(queryUsers);
            statementUsers.setLong(1, id);
            statementUsers.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete User with id - " + id, e);
        }
    }

    private User getUserFromResultSet(Connection connection, ResultSet resultSet)
            throws SQLException {
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, login, password);
        user.setUserId(userId);
        user.setSalt(salt);
        String query = "SELECT * FROM users_roles "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                + "WHERE users_roles.user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        ResultSet resultSetRole = preparedStatement.executeQuery();
        selectRoleForUser(resultSetRole, user);
        return user;
    }

    private void selectRoleForUser(ResultSet resultSetRole, User user)
            throws SQLException {
        Set<Role> roleSet = new HashSet<>();
        while (resultSetRole.next()) {
            long roleId = resultSetRole.getLong("role_id");
            String roleString = resultSetRole.getString("role_name");
            Role role = Role.of(roleString);
            role.setId(roleId);
            roleSet.add(role);
        }
        user.setRoles(roleSet);
    }

    private void insertRoleUserToDB(User user, Connection connection) throws SQLException {
        String queryRole = "SELECT role_id FROM roles WHERE role_name = ?";
        PreparedStatement preparedStatementRole = connection.prepareStatement(queryRole);
        String queryUserRole = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        PreparedStatement preparedStatementUserRole =
                connection.prepareStatement(queryUserRole);
        for (Role role : user.getRoles()) {
            preparedStatementRole.setString(1, String.valueOf(role.getRoleName()));
            ResultSet resultSet = preparedStatementRole.executeQuery();
            if (resultSet.next()) {
                long roleId = resultSet.getLong("role_id");
                preparedStatementUserRole.setLong(1, user.getUserId());
                preparedStatementUserRole.setLong(2, roleId);
                preparedStatementUserRole.executeUpdate();
            }
        }
    }
}
