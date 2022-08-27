package com;

import com.config.JDBCConfig;
import com.model.*;
import com.repository.JDBC.InvoiceJDBCRepository;
import com.service.*;
import com.util.Annotations;
import com.util.Autowired;
import com.util.TableCreator;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCexamples {

    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static final Random random = new Random();
    @Autowired
    private static PhoneService PHONE_SERVICE;
    @Autowired
    private static NotebookService NOTEBOOK_SERVICE;

    @Autowired
    private static InvoiceService INVOICE_SERVICE;

    private final static InvoiceJDBCRepository repository = new InvoiceJDBCRepository();

    public static void main(String[] args) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        TableCreator.tableChecker();
        Annotations.repositories();
        Annotations.autowiredFields();
        int createdPhones = 3;
        int createdNotebooks = 2;
        INVOICE_SERVICE.groupBySum();
        INVOICE_SERVICE.updateToCurrentTime("432652c6-8ac6-4604-830e-bafc01a389f3");
        System.out.println(INVOICE_SERVICE.getInvoicesExpensiveThen(3333.34));
        System.out.println(INVOICE_SERVICE.getCount());
        System.out.println(INVOICE_SERVICE.findById("432652c6-8ac6-4604-830e-bafc01a389f3"));
        List<Phone> phones = PHONE_SERVICE.findProducts(createdPhones);
        List<Notebook> notebooks = NOTEBOOK_SERVICE.findProducts(createdNotebooks);
        List<Product> products = new ArrayList<>();
        products.addAll(phones);
        products.addAll(notebooks);
        Invoice invoice = new Invoice.InvoiceBuilder()
                .id(UUID.randomUUID().toString())
                .time(LocalDateTime.now())
                .products(products)
                .sum(sum(products))
                .build();
        repository.save(invoice);
        repository.findbyId("432652c6-8ac6-4604-830e-bafc01a389f3");



    }

    private static double sum(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }


    public static <T> T getFirst(List<? extends T> list) {
        return list.get(0); // compile-time error
    }
}
