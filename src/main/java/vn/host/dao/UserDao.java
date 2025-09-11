package vn.host.dao;

import vn.host.entity.User;

public interface UserDao {
    User findUserByName(String username);
    boolean updateProfile(int id, String fullname, String phone, String imageFileName);

    User findUserById(int id);
}
