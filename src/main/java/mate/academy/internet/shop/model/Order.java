package mate.academy.internet.shop.model;

import java.util.List;

public class Order {

    private Long orderId;
    private Long userId;
    private List<Product> products;

    public Order(Long userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + orderId + ", userId=" + userId
                + ", products=" + products + '}';
    }
}
