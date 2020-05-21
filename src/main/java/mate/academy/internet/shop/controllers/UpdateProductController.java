package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.service.ProductService;

public class UpdateProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.valueOf(req.getParameter("productId"));
        Product product = productService.get(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/views/updateProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long productId = Long.valueOf(req.getParameter("productId"));
        String name = req.getParameter("name");
        Double price = Double.valueOf(req.getParameter("price"));
        Product product = productService.get(productId);
        product.setName(name);
        product.setPrice(price);
        productService.update(product);
        resp.sendRedirect(req.getContextPath() + "/products/allForAdmin");
    }
}
