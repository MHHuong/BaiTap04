package vn.host.services;

import vn.host.entity.User;

public interface AuthService {
    User login(String username, String password);
}
