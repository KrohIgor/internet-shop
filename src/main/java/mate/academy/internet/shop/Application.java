package mate.academy.internet.shop;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.OrderService;
import mate.academy.internet.shop.service.ProductService;
import mate.academy.internet.shop.service.ShoppingCartService;
import mate.academy.internet.shop.service.UserService;

public class Application {

    private static Injector injector = Injector.getInstance("mate.academy.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        UserService userService = (UserService) injector.getInstance(UserService.class);

        testProductService(productService);

        testOrderService(orderService, userService, productService);

        testUserService(userService);

        ShoppingCartService shoppingCartService = (ShoppingCartService)
                injector.getInstance(ShoppingCartService.class);

        testShoppingCartService(shoppingCartService, userService, productService);

    }

    private static void testOrderService(OrderService orderService, UserService userService,
                                         ProductService productService) {
        User bob = new User("Bob", "bob", "111");
        User jack = new User("Jack", "jack", "222");
        userService.create(bob);
        userService.create(jack);

        Product apple = new Product("Apple", 5.5);
        Product tomato = new Product("Tomato", 10.5);
        Product cucumber = new Product("Cucumber", 7.5);
        productService.create(apple);
        productService.create(tomato);
        productService.create(cucumber);

        List<Product> listBob = new ArrayList<>();
        listBob.add(apple);
        listBob.add(tomato);
        listBob.add(cucumber);

        List<Product> listJack = new ArrayList<>();
        listJack.add(apple);
        listJack.add(tomato);

        Order bobOrder = orderService.completeOrder(listBob, bob);
        System.out.println("Test ShoppingCart Service:");
        System.out.println(orderService.get(bobOrder.getId()));
        System.out.println("All orders:");
        Order jackOrder = orderService.completeOrder(listJack, jack);
        System.out.println(orderService.getAll());
        orderService.delete(jackOrder.getId());
        System.out.println("Orders after delete jackOrder:");
        System.out.println(orderService.getAll());
        System.out.println("Bob's order :");
        System.out.println(orderService.getUserOrders(bob));
    }

    private static void testShoppingCartService(ShoppingCartService shoppingCartService,
                                                UserService userService,
                                                ProductService productService) {
        User bob = new User("Bob", "bob", "111");
        userService.create(bob);

        Product apple = new Product("Apple", 5.5);
        Product tomato = new Product("Tomato", 10.5);
        Product cucumber = new Product("Cucumber", 7.5);
        productService.create(apple);
        productService.create(tomato);
        productService.create(cucumber);

        ShoppingCart bobCart = new ShoppingCart(bob);

        System.out.println("Test ShoppingCart Service:");
        shoppingCartService.addProduct(bobCart, apple);
        shoppingCartService.addProduct(bobCart, tomato);
        System.out.println("All Bob's products: " + shoppingCartService.getAllProducts(bobCart));
        System.out.println("ShoppingCart Bob: " + shoppingCartService.getByUserId(bob.getId()));
        shoppingCartService.addProduct(bobCart, cucumber);
        System.out.println("ShoppingCart Bob with added: Cucumber"
                + shoppingCartService.getByUserId(bob.getId()));
        shoppingCartService.deleteProduct(bobCart, tomato);
        System.out.println("ShoppingCart Bob after deleted Tomato:"
                + shoppingCartService.getByUserId(bob.getId()));
        shoppingCartService.clear(bobCart);
        System.out.println("ShoppingCart Bob after clear:"
                + shoppingCartService.getByUserId(bob.getId()));
    }

    private static void testUserService(UserService userService) {
        User bob = new User("Bob", "bob", "111");
        User jack = new User("Jack", "jack", "222");
        User nik = new User("Nik", "nik", "333");

        userService.create(bob);
        userService.create(jack);
        userService.create(nik);

        System.out.println("Test User Service:");
        for (User user : userService.getAll()) {
            System.out.println(user);
        }

        System.out.println("Get Jack by id:");
        System.out.println(userService.get(jack.getId()));

        System.out.println("Jack changed name on Pit:");
        jack.setName("Pit");
        userService.update(jack);
        for (User user : userService.getAll()) {
            System.out.println(user);
        }

        System.out.println("Deleted user Bob:");
        userService.delete(bob.getId());
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
    }

    private static void testProductService(ProductService productService) {
        Product apple = new Product("Apple", 5.5);
        Product tomato = new Product("Tomato", 10.5);
        Product cucumber = new Product("Cucumber", 7.5);
        productService.create(apple);
        productService.create(tomato);
        productService.create(cucumber);

        System.out.println("Test Product Service:");
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }

        System.out.println("Tomato changed name on Watermelon and price on 15.0:");
        tomato.setName("Watermelon");
        tomato.setPrice(15.0);
        productService.update(tomato);
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }

        System.out.println("Deleted Tomato:");
        productService.delete(tomato.getId());
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
    }
}
