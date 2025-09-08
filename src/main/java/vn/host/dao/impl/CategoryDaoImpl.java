package vn.host.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.host.configs.JPAConfig;
import vn.host.dao.CategoryDao;
import vn.host.entity.Category;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public void create(Category category) {
        EntityManager emma = JPAConfig.getEntityManager();
        EntityTransaction trans = emma.getTransaction();
        try{
            trans.begin();
            emma.persist(category);
            trans.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            emma.close();
        }
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Category delete(Category category) {
        return null;
    }

    @Override
    public Category findById(int id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }
}
