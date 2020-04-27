package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.ProductService;
import mate.academy.internet.shop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User bob = new User("Bob");
        bob.setLogin("bob");
        User alisa = new User("Alisa");
        alisa.setLogin("alisa");
        userService.create(bob);
        userService.create(alisa);

        Product apple = new Product("Apple", 5.5);
        Product tomato = new Product("Tomato", 9.7);
        Product orange = new Product("Orange", 12.5);
        productService.create(apple);
        productService.create(tomato);
        productService.create(orange);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
