package mate.academy.internet.shop.service;

import java.util.List;
import mate.academy.internet.shop.model.User;

public interface UserService {

    User create(User user);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);
}
