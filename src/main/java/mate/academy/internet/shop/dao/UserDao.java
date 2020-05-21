package mate.academy.internet.shop.dao;

import java.util.Optional;
import mate.academy.internet.shop.model.User;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> findByLogin(String login);
}
