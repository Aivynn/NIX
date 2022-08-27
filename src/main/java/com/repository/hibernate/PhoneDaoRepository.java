package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.PhoneDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;


public class PhoneDaoRepository implements CrudRepositoryDAO<PhoneDAO> {
    final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    @Override
    public void save(PhoneDAO product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(PhoneDAO product) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PhoneDAO getById(String id) {
        return sessionFactory.openSession().find(PhoneDAO.class, id);
    }
}
