package mate.academy.internet.shop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Role;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.ShoppingCartService;
import mate.academy.internet.shop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User bob = new User("Bob");
        bob.setLogin("bob");
        bob.setPassword("1");
        bob.setRoles(Set.of(Role.of("USER")));
        User alisa = new User("Alisa");
        alisa.setLogin("alisa");
        alisa.setPassword("2");
        alisa.setRoles(Set.of(Role.of("USER")));
        userService.create(bob);
        userService.create(alisa);

        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("3");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);

        ShoppingCart bobShoppingCart = new ShoppingCart(bob.getUserId());
        shoppingCartService.create(bobShoppingCart);

        ShoppingCart adminShoppingCart = new ShoppingCart(admin.getUserId());
        shoppingCartService.create(adminShoppingCart);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
