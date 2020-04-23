package mate.academy.internet.shop.model;

import java.util.List;

public class Order {

    private Long id;
    private User user;
    private List<Product> products;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", user=" + user
                + ", products=" + products + '}';
    }
}
