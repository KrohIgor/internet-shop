package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Order;
import mate.academy.internet.shop.service.OrderService;

public class ViewOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        Order order = orderService.get(Long.valueOf(orderId));
        req.setAttribute("orderId", order.getOrderId());
        req.setAttribute("userName", order.getUser().getName());
        req.setAttribute("products",order.getProducts());
        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
