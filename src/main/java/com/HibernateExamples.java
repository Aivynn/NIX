package com;

import com.model.DAO.*;
import com.service.DaoServices.InvoiceDAOService;
import com.service.DaoServices.NotebookDaoService;
import com.service.DaoServices.PhoneDaoService;
import com.service.DaoServices.SmartwatchDaoService;
import com.util.Annotations;
import com.util.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HibernateExamples {

    @Autowired
    private static PhoneDaoService PHONE_SERVICE;

    @Autowired
    private static NotebookDaoService NOTEBOOK_SERVICE;

    @Autowired
    private static SmartwatchDaoService SMARTWATCH_SERVICE;

    @Autowired
    private static InvoiceDAOService INVOICE_SERVICE;


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, SQLException {

        Annotations.repositories();
        Annotations.autowiredFields();
        List<PhoneDAO> phones = PHONE_SERVICE.createAndSaveProducts(3);
        List<NotebookDAO> notebooks = NOTEBOOK_SERVICE.createAndSaveProducts(2);
        List<SmartwatchDAO> smartwatches = SMARTWATCH_SERVICE.createAndSaveProducts(2);

        List<ProductDAO> productDAOS = new ArrayList<>();
        productDAOS.addAll(phones);
        productDAOS.addAll(notebooks);
        productDAOS.addAll(smartwatches);
        InvoiceDAO invoiceDAO = new InvoiceDAO.InvoiceBuilder()
                .sum(getAverageSum(productDAOS))
                .time(LocalDateTime.now())
                .products(productDAOS)
                .build();
        INVOICE_SERVICE.createAndSave(invoiceDAO);
        INVOICE_SERVICE.getInvoicesExpensiveThen(2000);
        INVOICE_SERVICE.groupBySum();
        INVOICE_SERVICE.getCount();
        INVOICE_SERVICE.updateToCurrentTime("ca3d7f2b-c32a-48a8-b376-1a24cda64848");
    }

    private static Double getAverageSum(List<ProductDAO> productDAOS) {
        return productDAOS.stream().mapToDouble(ProductDAO::getPrice).sum();
    }
}
