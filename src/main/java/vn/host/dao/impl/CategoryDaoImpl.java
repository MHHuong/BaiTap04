package vn.host.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.host.dao.CategoryDao;
import vn.host.entity.Category;
import vn.host.untils.JpaUntil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Category c ORDER BY c.categoryid DESC", Category.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findByUserId(int userId) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Category c WHERE c.owner.userid = :uid ORDER BY c.categoryid DESC",
                    Category.class
            ).setParameter("uid", userId).getResultList();
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
    public Category findOwnedById(int id, int ownerId) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Category c WHERE c.categoryid = :id AND c.owner.userid = :uid",
                    Category.class
            ).setParameter("id", id).setParameter("uid", ownerId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean insert(Category c) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Category c) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            em.getTransaction().begin();
            Category c = em.find(Category.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
}

