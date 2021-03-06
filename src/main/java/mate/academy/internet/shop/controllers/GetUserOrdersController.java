package mate.academy.internet.shop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.service.OrderService;

public class GetUserOrdersController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        List<Order> userOrders = orderService.getUserOrders(userId);
        req.setAttribute("orders", userOrders);
        req.getRequestDispatcher("/WEB-INF/views/user/orders.jsp").forward(req, resp);
    }
}
