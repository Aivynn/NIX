package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.InvoiceDAO;
import com.model.DAO.PhoneDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InvoiceDaoRepository {
    final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    public void save(InvoiceDAO invoice) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(invoice);
        session.flush();
        session.getTransaction().commit();
    }

    public void update(InvoiceDAO product) {
    }


    public void delete(String id) {
    }

    public InvoiceDAO getById(String id) {
        return sessionFactory.openSession().find(InvoiceDAO.class, id);
    }


}
