package mate.academy.internet.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.OrderService;
import org.apache.log4j.Logger;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        if (products == null) {
            LOGGER.warn("Count product 0, order not complete!");
            return null;
        }
        List<Product> copyProducts = new ArrayList<>(products);
        Order order = new Order(user.getUserId(), copyProducts);
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getAll().stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Order create(Order element) {
        return orderDao.create(element);
    }

    @Override
    public Order get(Long id) {
        return orderDao.getAll()
                .stream()
                .filter(o -> o.getOrderId().equals(id))
                .findFirst().get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order element) {
        return orderDao.update(element);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.getAll().removeIf(o -> o.getOrderId().equals(id));
    }
}
