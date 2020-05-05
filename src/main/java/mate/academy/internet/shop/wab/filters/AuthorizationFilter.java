package mate.academy.internet.shop.wab.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Role;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internet.shop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/addProduct", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/deleteUser", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/deleteOrder", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/deleteProduct", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/allForAdministrator", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/complete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/addProductToShoppingCart", Set.of(Role.RoleName.USER));
        protectedUrls.put("/deleteProductFromShoppingCart", Set.of(Role.RoleName.USER));
        protectedUrls.put("/allProductInShoppingCart", Set.of(Role.RoleName.USER));
        protectedUrls.put("/user/orders", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();
        if (protectedUrls.get(requestedUrl) == null) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
