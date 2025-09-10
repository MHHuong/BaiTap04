package vn.host.dao;

import vn.host.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    List<Category> findByUserId(int userId);

    Category findById(int id);

    Category findOwnedById(int id, int ownerId); // dùng cho user

    boolean insert(Category c);

    boolean update(Category c);

    boolean delete(int id);
}
