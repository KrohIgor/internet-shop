package mate.academy.internet.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderDao orderDao;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        Order order = new Order();
        order.setProducts(products);
        order.setUser(user);
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream().filter(o -> o.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long id) {
        return orderDao.getAll()
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.getAll().removeIf(o -> o.getId().equals(id));
    }
}
