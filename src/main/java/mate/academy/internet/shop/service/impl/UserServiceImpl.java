package mate.academy.internet.shop.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.dao.UserDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.ShoppingCartService;
import mate.academy.internet.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private ShoppingCartService shoppingCartService;

    @Inject
    private UserDao userDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        ShoppingCart shoppingCartByUserId = shoppingCartService.getByUserId(id);
        shoppingCartDao.delete(shoppingCartByUserId.getShoppingCartId());
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
