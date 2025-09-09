package vn.host.dao;

import vn.host.entity.User;

public interface UserDao {
    User findUserByName(String username);

    User findUserById(int id);
}
