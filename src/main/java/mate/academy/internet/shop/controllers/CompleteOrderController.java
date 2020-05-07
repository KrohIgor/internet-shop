package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.OrderService;
import mate.academy.internet.shop.service.ShoppingCartService;
import mate.academy.internet.shop.service.UserService;
import org.apache.log4j.Logger;

public class CompleteOrderController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CompleteOrderController.class);
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        User user = userService.get(userId);
        Order order = orderService.completeOrder(shoppingCart.getProducts(), user);
        if (order != null) {
            shoppingCartService.clear(shoppingCart);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "Count product 0, order not complete!");
            req.getRequestDispatcher("/WEB-INF/views/shoppingCart.jsp").forward(req, resp);
            LOGGER.warn("Order not complete for userId: " + userId);
        }
    }
}
