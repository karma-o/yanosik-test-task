package org.yanosik.test.task.server.service;

import org.yanosik.test.task.common.model.User;

public interface UserService {
    User add(User user);

    User getById(Long id);

    User update(User user);

    boolean delete(long id);
}
