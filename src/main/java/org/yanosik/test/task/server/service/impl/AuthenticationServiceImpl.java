package org.yanosik.test.task.server.service.impl;

import javax.security.sasl.AuthenticationException;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.server.service.AuthenticationService;
import org.yanosik.test.task.server.service.UserService;

/**
 * Provides authentication logic on some basic level.
 * I would say that it is not a good idea to use this class in production,
 * Because it is not secure enough along with the password field itself.
 * Possible solution for that is to use some kind of hashing algorithm,
 * and NOT send the password via the network as a plain text in JSON object.
 * But for the sake of simplicity and keeping with the main task, I decided to use this class.
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public User login(Long id, String password) throws AuthenticationException {
        System.out.println("Checking user with id = " + id);
        User user = userService.getById(id);
        if (user.getPassword().equals(password)) {
            System.out.println("User with id = " + id + " is authenticated");
            return user;
        }
        throw new AuthenticationException("Invalid password for user with id: " + id);
    }
}
