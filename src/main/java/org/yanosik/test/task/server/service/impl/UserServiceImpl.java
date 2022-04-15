package org.yanosik.test.task.server.service.impl;

import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.server.dao.UserDao;
import org.yanosik.test.task.server.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User add(User user) {
        return userDao.create(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(long id) {
        return userDao.delete(id);
    }

    public boolean delete(User user) {
        return userDao.delete(user.getId());
    }
}
