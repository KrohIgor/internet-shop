package mate.academy.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internet.shop.exception.AuthenticationException;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.security.AuthenticationService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final AuthenticationService authService =
            (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");

        try {
            User user = authService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("user_id", user.getUserId());
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            LOGGER.error(e.getMessage(), e);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
