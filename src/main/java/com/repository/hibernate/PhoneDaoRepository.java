package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.PhoneDAO;
import com.util.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class PhoneDaoRepository implements CrudRepositoryDAO<PhoneDAO> {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private final Session session = sessionFactory.openSession();

    @Override
    public void save(PhoneDAO product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(String id) {
        session.getTransaction().begin();
        Query query = session.createQuery("update PhoneDAO phone set phone.time=:t where phone.id=:i");
        query.setParameter("t", LocalDateTime.now());
        query.setParameter("i", id);
        int status = query.executeUpdate();
        System.out.println(status);
        session.flush();
        session.getTransaction().commit();

    }

    @Override
    public void delete(String id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.getTransaction().begin();
        Query query = session.createQuery("delete PhoneDAO where id=:i");
        query.setParameter("i", id);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public PhoneDAO getById(String id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from PhoneDAO where id =: i");
        query.setParameter("i", id);
        List<PhoneDAO> phoneDAOS = query.list();
        return phoneDAOS.get(0);

    }

    @Override
    public void saveAll(List<PhoneDAO> products) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(PhoneDAO phoneDAO : products) {
            session.save(phoneDAO);
        }
        session.getTransaction().commit();
        session.close();
    }


}
