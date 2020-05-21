package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.service.OrderService;

public class DeleteOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderId = req.getParameter("orderId");
        orderService.delete(Long.valueOf(orderId));
        resp.sendRedirect(req.getContextPath() + "/orders/all");
    }
}
