package org.yanosik.test.task.server.service.impl;

import javax.security.sasl.AuthenticationException;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.server.service.AuthenticationService;
import org.yanosik.test.task.server.service.UserService;

/**
 * Provides authentication logic on some basic level.
 *
 * Also, this class needs a secure method of delivering a password to the server
 * and a hashing algorithm (like SHA-256)
 * to hash a password of the registering user, and to compare a password while logging in
 * But by doing that we also need to modify the database, DAO, DTO mappers and the Model.
 * The main logic would be pretty much the same.
 *
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
