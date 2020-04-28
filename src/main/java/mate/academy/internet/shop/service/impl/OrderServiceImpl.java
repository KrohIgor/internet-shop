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

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        if (products.size() == 0) {
            System.out.println("Count product 0, order not complete!");
            return null;
        }
        List<Product> copyProducts = new ArrayList<>(products);
        Order order = new Order(user, copyProducts);
        orderDao.create(order);
        shoppingCartDao.getAll()
                .stream()
                .filter(s -> s.getUser().getUserId().equals(user.getUserId()))
                .findFirst()
                .ifPresent(s -> s.getProducts().clear());
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream()
                .filter(o -> o.getUser().getUserId().equals(user.getUserId()))
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
                .filter(o -> o.getId().equals(id))
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
        return orderDao.getAll().removeIf(o -> o.getId().equals(id));
    }
}
