package mate.academy.internet.shop.db;

import mate.academy.internet.shop.model.Bucket;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static Long userId = 0L;
    private static Long bucketId = 0L;
    private static Long orderId = 0L;
    private static Long productId = 0L;

    public static final List<Product> products = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();

    public static void add(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
    }

    public static void add(Bucket bucket) {
        bucketId++;
        bucket.setId(bucketId);
        buckets.add(bucket);
    }

    public static void add(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
    }

    public static void add(Order order) {
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }
}
