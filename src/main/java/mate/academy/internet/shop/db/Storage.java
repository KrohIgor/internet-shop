package mate.academy.internet.shop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;

public class Storage {

    public static final List<Product> products = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();

    private static Long userId = 0L;
    private static Long shoppingCartId = 0L;
    private static Long orderId = 0L;
    private static Long productId = 0L;

    public static void add(Product product) {
        productId++;
        product.setProductId(productId);
        products.add(product);
    }

    public static void add(ShoppingCart shoppingCart) {
        shoppingCartId++;
        shoppingCart.setId(shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void add(User user) {
        userId++;
        user.setUserId(userId);
        users.add(user);
    }

    public static void add(Order order) {
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }
}
