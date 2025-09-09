package vn.host.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.host.dao.CategoryDao;
import vn.host.entity.Category;
import vn.host.untils.JpaUntil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM  Category c ORDER BY c.categoryid DESC ", Category.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findByUserId(int userId) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c WHERE c.owner.userid =:userid ORDER BY c.categoryid DESC", Category.class).setParameter("userid", userId).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findOwnedById(int id, int ownerId) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            List<Category> rs = em.createQuery(
                    "SELECT c FROM Category c WHERE c.categoryid = :id AND c.owner.userid = :uid",
                    Category.class).setParameter("id", id).setParameter("uid", ownerId).getResultList();
            return rs.isEmpty() ? null : rs.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Category c) {
        EntityManager em = JpaUntil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(c);
            tx.commit();

        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateOwned(Category c, int ownerId) {
        EntityManager em = JpaUntil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category db = em.find(Category.class, c.getCategoryid());
            if (db != null || !db.getOwner().getUserid().equals(ownerId)) {
                tx.rollback();
                return false;
            }
            db.setCategoryname(c.getCategoryname());
            db.setImages(c.getImages());
            db.setStatus(c.getStatus());
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteOwned(int id, int ownerId) {
        EntityManager em = JpaUntil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category db = em.find(Category.class, id);
            if (db != null || !db.getOwner().getUserid().equals(ownerId)) {
                tx.rollback();
                return false;
            }
            em.remove(db);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}

