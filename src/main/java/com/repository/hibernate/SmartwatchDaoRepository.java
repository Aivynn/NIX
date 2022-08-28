package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.PhoneDAO;
import com.model.DAO.SmartwatchDAO;
import com.util.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class SmartwatchDaoRepository implements CrudRepositoryDAO<SmartwatchDAO> {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private final Session session = sessionFactory.openSession();

    @Override
    public void save(SmartwatchDAO product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(String id) {
        session.getTransaction().begin();
        Query query = session.createQuery("update SmartwatchDAO smartwatch set smartwatch.time=:t where smartwatch.id=:i");
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
        Query query = session.createQuery("delete SmartwatchDAO where id=:i");
        query.setParameter("i", id);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public SmartwatchDAO getById(String id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from PhoneDAO where id =: i");
        query.setParameter("i", id);
        List<SmartwatchDAO> SmartwatchDAO = query.list();
        return SmartwatchDAO.get(0);

    }

    @Override
    public void saveAll(List<SmartwatchDAO> products) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(SmartwatchDAO smartwatchDAO : products) {
            session.save(smartwatchDAO);
        }
        session.getTransaction().commit();
        session.close();
    }
}
