package vn.host.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.host.dao.UserDao;
import vn.host.entity.User;
import vn.host.untils.JpaUntil;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByName(String username) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :u", User.class).setParameter("u", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateProfile(int id, String fullname, String phone, String imageFileName) {
        EntityManager em = JpaUntil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, id);
            if (u == null) { tx.rollback(); return false; }
            u.setFullname(fullname);
            u.setPhone(phone);
            if (imageFileName != null) {
                u.setImages(imageFileName);
            }
            em.merge(u);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public User findUserById(int id) {
        EntityManager em = JpaUntil.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

}
