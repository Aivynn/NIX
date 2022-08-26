package com.service;

import com.model.Invoice;
import com.repository.JDBC.InvoiceJDBCRepository;
import com.util.Autowired;
import com.util.Singleton;

import java.sql.SQLException;
import java.util.List;

@Singleton
public class InvoiceService {

    InvoiceJDBCRepository repository;

    @Autowired
    public InvoiceService(InvoiceJDBCRepository invoiceJDBCRepository){
        this.repository = invoiceJDBCRepository;
    }

    public Invoice findById(String id){
       return repository.findbyId(id);
    }

    public void createAndSave(Invoice invoice) throws SQLException {
        repository.save(invoice);
    }

    public int getCount(){
        return repository.countRows();
    }

    public void updateToCurrentTime(String id){
        repository.update(id);
    }

    public List<Invoice> getInvoicesExpensiveThen(double sum){
        List<Invoice> invoiceList = repository.findBySum(sum);
        return invoiceList;
    }

    public void groupBySum() {
        repository.GroupBySum();
    }

}
