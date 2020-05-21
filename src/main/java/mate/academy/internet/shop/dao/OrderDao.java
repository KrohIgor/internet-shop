package mate.academy.internet.shop.dao;

import java.util.List;
import mate.academy.internet.shop.model.Order;

public interface OrderDao extends GenericDao<Order, Long> {

    List<Order> getUserOrders(Long userId);
}
