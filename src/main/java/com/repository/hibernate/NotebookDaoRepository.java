package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.NotebookDAO;
import com.model.DAO.PhoneDAO;
import com.util.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class NotebookDaoRepository implements CrudRepositoryDAO<NotebookDAO> {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private final Session session = sessionFactory.openSession();

    @Override
    public void save(NotebookDAO product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(String id) {
        session.getTransaction().begin();
        Query query = session.createQuery("update NotebookDAO notebook set notebook.time=:t where notebook.id=:i");
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
        Query query = session.createQuery("delete NotebookDAO where id=:i");
        query.setParameter("i", id);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public NotebookDAO getById(String id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("from NotebookDAO where id =: i");
        query.setParameter("i", id);
        List<NotebookDAO> NotebookDAOS = query.list();
        return NotebookDAOS.get(0);

    }

    @Override
    public void saveAll(List<NotebookDAO> products) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(NotebookDAO notebookDAO : products) {
            session.save(notebookDAO);
        };
        session.getTransaction().commit();
        session.close();
    }
}
