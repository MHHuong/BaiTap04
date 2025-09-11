package vn.host.services.impl;

import vn.host.dao.impl.UserDaoImpl;
import vn.host.entity.User;
import vn.host.services.AuthService;

public class AuthServiceImpl implements AuthService {

    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {

        User u = userDao.findUserByName(username);
        if (u == null) return null;

        if (u.getPassword() != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
}