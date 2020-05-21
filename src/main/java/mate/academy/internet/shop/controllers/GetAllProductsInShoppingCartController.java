package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.service.ShoppingCartService;

public class GetAllProductsInShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        req.setAttribute("products", shoppingCart.getProducts());
        req.getRequestDispatcher("/WEB-INF/views/shoppingCart.jsp").forward(req, resp);
    }
}
