package mate.academy.internet.shop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.UserService;

public class UsersController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User bob = new User("Bob");
        User alisa = new User("Alisa");
        userService.create(bob);
        userService.create(alisa);
        List<User> allUsers = userService.getAll();

        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/views/users/all.jsp").forward(req, resp);
    }
}
