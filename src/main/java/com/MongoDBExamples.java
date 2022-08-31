package com;

import com.model.*;
import com.model.mongoInvoice.InvoiceMongo;
import com.service.mongoServices.InvoiceMongoService;
import com.service.mongoServices.NotebookMongoService;
import com.service.mongoServices.PhoneMongoService;
import com.service.mongoServices.SmartwatchMongoService;
import com.util.Annotations;
import com.util.Autowired;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MongoDBExamples {

    @Autowired
    private static PhoneMongoService PHONE_SERVICE;
    @Autowired
    private static NotebookMongoService NOTEBOOK_SERVICE;
    @Autowired
    private static SmartwatchMongoService SMARTWATCH_SERVICE;

    @Autowired
    private static InvoiceMongoService INVOICE_SERVICE;


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Annotations.repositories();
        Annotations.autowiredFields();
        List<Phone> phones = PHONE_SERVICE.createAndSaveProducts(3);
        List<Notebook> notebooks = NOTEBOOK_SERVICE.createAndSaveProducts(2);
        List<Smartwatch> smartwatches = SMARTWATCH_SERVICE.createAndSaveProducts(2);

        List<Product> products = new ArrayList<>();
        products.addAll(phones);
        products.addAll(notebooks);
        products.addAll(smartwatches);
        InvoiceMongo invoiceMongo = new InvoiceMongo.InvoiceBuilder()
                .sum(getAverageSum(products))
                .time(LocalDateTime.now())
                .products(products)
                .build();
        String id = "630f656f024a9d72a656d078";
        INVOICE_SERVICE.createAndSave(invoiceMongo);
        INVOICE_SERVICE.findBySum(2000);
        INVOICE_SERVICE.groupBySum();
        INVOICE_SERVICE.countDocuments();
        //INVOICE_SERVICE.updateTime(id);
    }

    private static Double getAverageSum(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
