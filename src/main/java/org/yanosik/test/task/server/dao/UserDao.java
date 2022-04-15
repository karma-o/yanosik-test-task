package org.yanosik.test.task.server.dao;

import java.util.Optional;
import org.yanosik.test.task.common.model.User;

public interface UserDao {
    User create(User user);

    Optional<User> getById(Long id);

    User update(User user);

    boolean delete(Long id);
}
