package vn.host.services.impl;

import vn.host.dao.UserDao;
import vn.host.dao.impl.UserDaoImpl;
import vn.host.entity.User;
import vn.host.services.AuthService;

public class AuthServiceImpl implements AuthService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User u = userDao.findUserByName(username);
        return (u != null && u.getPassword().equals(password)) ? u : null;
    }
}
