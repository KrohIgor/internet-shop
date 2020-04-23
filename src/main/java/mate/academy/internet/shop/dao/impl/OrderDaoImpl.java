package mate.academy.internet.shop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internet.shop.dao.OrderDao;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
