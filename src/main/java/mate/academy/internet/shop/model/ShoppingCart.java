package mate.academy.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private User user;
    private Long id;
    private List<Product> products;

    public ShoppingCart(User user) {
        this.user = user;
        products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "user=" + user + ", id=" + id
                + ", products=" + products + '}';
    }
}
