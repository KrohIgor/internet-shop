package mate.academy.internet.shop.security;

import mate.academy.internet.shop.controllers.RegistrationController;
import mate.academy.internet.shop.exception.AuthenticationException;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.User;
import mate.academy.internet.shop.service.UserService;
import org.apache.log4j.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDB = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect user name or password"));
        if (userFromDB.getPassword().equals(password)) {
            return userFromDB;
        }
        LOGGER.warn("Incorrect user name or password");
        throw new AuthenticationException("Incorrect user name or password");
    }
}
