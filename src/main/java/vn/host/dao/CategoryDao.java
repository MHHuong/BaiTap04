package vn.host.dao;

import vn.host.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    List<Category> findByUserId(int userId);

    Category findOwnedById(int id, int ownerId);

    Category findById(int id);

    void insert(Category c);

    boolean updateOwned(Category c, int ownerId);

    boolean deleteOwned(int id, int ownerId);
}
