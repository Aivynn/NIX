package com.service;

import com.model.DAO.InvoiceDAO;
import com.model.Invoice;
import com.repository.JDBC.InvoiceJDBCRepository;
import com.repository.hibernate.InvoiceDaoRepository;
import com.util.Autowired;
import com.util.Singleton;

import java.sql.SQLException;
import java.util.List;

@Singleton
public class InvoiceService {

    InvoiceDaoRepository repository;

    @Autowired
    public InvoiceService(InvoiceDaoRepository invoiceDaoCRepository){
        this.repository = invoiceDaoCRepository;
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
