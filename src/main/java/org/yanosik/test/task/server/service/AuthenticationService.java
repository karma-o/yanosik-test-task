package org.yanosik.test.task.server.service;

import javax.security.sasl.AuthenticationException;
import org.yanosik.test.task.common.model.User;

public interface AuthenticationService {
    User login(Long id, String password) throws AuthenticationException;
}
