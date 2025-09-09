package vn.host.dao.impl;

import jakarta.persistence.EntityManager;
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
    public User findUserById(int id) {
        return null;
    }

}
