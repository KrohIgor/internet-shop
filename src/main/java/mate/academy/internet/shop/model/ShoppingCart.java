package mate.academy.internet.shop.model;

import java.util.List;

public class ShoppingCart {

    private User user;
    private Long shoppingCartId;
    private List<Product> products;

    public ShoppingCart(User user) {
        this.user = user;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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
        return "ShoppingCart{" + "user=" + user + ", id=" + shoppingCartId
                + ", products=" + products + '}';
    }
}
