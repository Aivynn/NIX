package com.repository.hibernate;

import com.config.HibernateSessionFactoryUtil;
import com.model.DAO.InvoiceDAO;
import com.util.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class InvoiceDaoRepository {
    final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private final Session session = sessionFactory.openSession();

    public void save(InvoiceDAO invoice) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(invoice);
        session.flush();
        session.getTransaction().commit();
    }

    public List<InvoiceDAO> findBySum(double sum) {
        session.getTransaction().begin();
        Query query = session.createQuery("from Invoice where averageSum >:i");
        query.setParameter("i", sum);
        List<InvoiceDAO> invoiceDAOS = query.list();
        session.flush();
        session.getTransaction().commit();
        return invoiceDAOS;
    }

    public void groupBySum() {
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT count(id) as id, averageSum from Invoice Group by averageSum");
        List<Object[]> groupList = query.list();
        groupList.forEach(x -> {
            System.out.println(x[0] + " " + x[1]);
        });
        session.flush();
        session.getTransaction().commit();
    }

    public Long countRows() {
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT count(id) from Invoice");
        List<Long> groupList = query.list();
        groupList.forEach(System.out::println);
        session.flush();
        session.getTransaction().commit();
        return groupList.get(0);
    }

    public void updateTime(String id) {
        session.getTransaction().begin();
        Query query = session.createQuery("update Invoice invoice set invoice.time=:t where invoice.id=:i");
        query.setParameter("t", LocalDateTime.now());
        query.setParameter("i", id);
        int status = query.executeUpdate();
        System.out.println(status);
        session.flush();
        session.getTransaction().commit();
    }

    public InvoiceDAO getById(String id) {
        return sessionFactory.openSession().find(InvoiceDAO.class, id);
    }


}
