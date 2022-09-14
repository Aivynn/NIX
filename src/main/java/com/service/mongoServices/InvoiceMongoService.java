package com.service.mongoServices;

import com.model.mongoInvoice.InvoiceMongo;
import com.repository.mongo.InvoiceMongoRepository;
import com.util.Autowired;
import com.util.Singleton;

import java.util.List;

@Singleton
public class InvoiceMongoService {

    private InvoiceMongoRepository repository;

    @Autowired
    public InvoiceMongoService(InvoiceMongoRepository invoiceMongoRepository) {
        this.repository = invoiceMongoRepository;
    }


        public void createAndSave(InvoiceMongo invoiceMongo) {
        repository.save(invoiceMongo);
    }

    public List<InvoiceMongo> findBySum(double sum) {
        return repository.findBySum(sum);
    }

    public void countDocuments() {
        repository.countRows();
    }

    public void findById(String id) {
        repository.findById(id);
    }

    public void groupBySum() {
        repository.groupBySum();
    }

    public void updateTime(String id){
        repository.updateTime(id);
    }
}
