package vn.host.dao;
import java.util.List;
import vn.host.entity.Category;

public interface CategoryDao {
    void create(Category category);
    Category update(Category category);
    Category delete(Category category);
    Category findById(int id);
    List<Category> findAll();
}
