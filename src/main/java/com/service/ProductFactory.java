package com.service;

import com.model.Notebook;
import com.model.Phone;
import com.model.ProductType;
import com.model.Smartwatch;


public class ProductFactory {

    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<Notebook> NOTEBOOK_SERVICE = NotebookService.getInstance();
    private static final ProductService<Smartwatch>  SMARTWATCH_SERVICE = SmartwatchService.getInstance();

    private ProductFactory() {
    }

    public static void createAndSave(ProductType type) {
        switch (type) {
            case PHONE -> PHONE_SERVICE.createAndSaveProducts(1);
            case NOTEBOOK -> NOTEBOOK_SERVICE.createAndSaveProducts(1);
            case SMARTWATCH -> SMARTWATCH_SERVICE.createAndSaveProducts(1);
            default -> throw new IllegalArgumentException("Unknown Product type: " + type);
        };
    }

}