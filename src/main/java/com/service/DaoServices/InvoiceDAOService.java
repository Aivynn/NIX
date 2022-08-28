package com.service.DaoServices;

import com.model.DAO.InvoiceDAO;
import com.repository.hibernate.InvoiceDaoRepository;
import com.util.Autowired;

import java.sql.SQLException;
import java.util.List;

public class InvoiceDAOService {
    InvoiceDaoRepository repository;

    @Autowired
    public InvoiceDAOService(InvoiceDaoRepository invoiceDaoRepository){
        this.repository = invoiceDaoRepository;
    }

    public InvoiceDAO findById(String id){
        return repository.getById(id);
    }

    public void createAndSave(InvoiceDAO invoice) throws SQLException {
        repository.save(invoice);
    }

    public Long getCount(){
        return repository.countRows();
    }

    public void updateToCurrentTime(String id){
        repository.updateTime(id);
    }

    public List<InvoiceDAO> getInvoicesExpensiveThen(double sum){
        List<InvoiceDAO> invoiceList = repository.findBySum(sum);
        return invoiceList;
    }

    public void groupBySum() {
        repository.groupBySum();
    }
}
