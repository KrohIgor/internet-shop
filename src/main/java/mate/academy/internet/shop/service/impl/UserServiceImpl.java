package mate.academy.internet.shop.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.dao.UserDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.OrderService;
import mate.academy.internet.shop.service.ShoppingCartService;
import mate.academy.internet.shop.service.UserService;
import mate.academy.internet.shop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private ShoppingCartService shoppingCartService;
    @Inject
    private OrderService orderService;
    @Inject
    private UserDao userDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private OrderDao orderDao;

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
        List<Order> orderList = orderService.getUserOrders(id);
        for (Order order : orderList) {
            orderDao.delete(order.getOrderId());
        }
        ShoppingCart shoppingCartByUserId = shoppingCartService.getByUserId(id);
        shoppingCartDao.delete(shoppingCartByUserId.getShoppingCartId());
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void setPassword(User user, String password) {
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        String hashPassword = HashUtil.hashPassword(password, salt);
        user.setPassword(hashPassword);
    }
}
