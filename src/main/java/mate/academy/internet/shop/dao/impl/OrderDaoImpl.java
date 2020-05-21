package mate.academy.internet.shop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.db.Storage;
import mate.academy.internet.shop.model.Order;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders
                .stream()
                .filter(o -> o.getOrderId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(x -> order.getOrderId().equals(Storage.orders.get(x).getOrderId()))
                .forEach(o -> Storage.orders.set(o, order));
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(o -> o.getOrderId().equals(id));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
