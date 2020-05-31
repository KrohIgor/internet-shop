package mate.academy.internet.shop.service;

import java.util.Optional;
import mate.academy.internet.shop.model.User;

public interface UserService extends GenericService<User, Long> {

    Optional<User> findByLogin(String login);
}
