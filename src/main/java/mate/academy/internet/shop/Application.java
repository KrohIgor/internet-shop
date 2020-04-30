package mate.academy.internet.shop;

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

        User bob = new User("Bob", "bob", "111");
        User jack = new User("Jack", "jack", "222");
        User nik = new User("Nik", "nik", "333");

        UserService userService = (UserService) injector.getInstance(UserService.class);

        userService.create(bob);
        userService.create(jack);
        userService.create(nik);
        Product apple = new Product("Apple", 5.5);
        Product tomato = new Product("Tomato", 10.5);
        Product cucumber = new Product("Cucumber", 7.5);

        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

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
        productService.delete(tomato.getProductId());
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
        tomato.setName("Tomato");
        productService.create(tomato);
        System.out.println("---------------------------------------------");

        System.out.println("Test User Service:");
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
        System.out.println("Get Jack by id:");
        System.out.println(userService.get(jack.getUserId()));

        System.out.println("Jack changed name on Pit:");
        jack.setName("Pit");
        userService.update(jack);
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
        System.out.println("Deleted user Bob:");
        userService.delete(bob.getUserId());
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
        userService.create(bob);
        System.out.println("---------------------------------------------");

        ShoppingCart bobCart = new ShoppingCart(bob);
        System.out.println("Test ShoppingCart Service:");

        ShoppingCartService shoppingCartService = (ShoppingCartService)
                injector.getInstance(ShoppingCartService.class);

        shoppingCartService.addProduct(bobCart, apple);
        shoppingCartService.addProduct(bobCart, tomato);
        System.out.println("All Bob's products: " + shoppingCartService.getAllProducts(bobCart));
        System.out.println("ShoppingCart Bob: " + shoppingCartService.getByUserId(bob.getUserId()));
        shoppingCartService.addProduct(bobCart, cucumber);
        System.out.println("ShoppingCart Bob with added Cucumber :");
        System.out.println(shoppingCartService.getByUserId(bob.getUserId()));
        shoppingCartService.deleteProduct(bobCart, tomato);
        System.out.println("ShoppingCart Bob after deleted Tomato:");
        System.out.println(shoppingCartService.getByUserId(bob.getUserId()));
        shoppingCartService.clear(bobCart);
        System.out.println("ShoppingCart Bob after clear:");
        System.out.println(shoppingCartService.getByUserId(bob.getUserId()));
        shoppingCartService.addProduct(bobCart, apple);
        shoppingCartService.addProduct(bobCart, tomato);
        System.out.println("---------------------------------------------");

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        System.out.println("Test Order Service:");
        System.out.println("Bob's order by id:");
        Order bobOrder = orderService.completeOrder(bobCart.getProducts(), bob);
        System.out.println(orderService.get(bobOrder.getOrderId()));
        System.out.println("Order on empty sopping cart:");
        orderService.completeOrder(bobCart.getProducts(), bob);
        System.out.println("All orders:");
        ShoppingCart jackCart = new ShoppingCart(jack);
        shoppingCartService.addProduct(jackCart, tomato);
        shoppingCartService.addProduct(jackCart, cucumber);
        Order jackOrder = orderService.completeOrder(jackCart.getProducts(), jack);
        System.out.println(orderService.getAll());
        orderService.delete(jackOrder.getOrderId());
        System.out.println("Orders after delete jackOrder:");
        System.out.println(orderService.getAll());
        shoppingCartService.addProduct(bobCart, cucumber);
        orderService.completeOrder(bobCart.getProducts(), bob);
        System.out.println("Bob's orders by user:");
        System.out.println(orderService.getUserOrders(bob));
    }
}
