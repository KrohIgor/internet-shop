package mate.academy.internet.shop.model;

import java.util.List;

public class ShoppingCart {

    private Long userId;
    private Long shoppingCartId;
    private List<Product> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "userId=" + userId + ", id=" + shoppingCartId
                + ", products=" + products + '}';
    }
}
