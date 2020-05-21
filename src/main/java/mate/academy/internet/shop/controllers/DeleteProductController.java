package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.service.ProductService;

public class DeleteProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        productService.delete(Long.valueOf(productId));
        resp.sendRedirect(req.getContextPath() + "/products/allForAdmin");
    }
}
