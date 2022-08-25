package com.service;

import com.model.Notebook;
import com.model.Phone;
import com.model.ProductType;
import com.model.Smartwatch;
import com.util.Autowired;

import java.util.Random;

public class ProductFactory {
    private static final Random RANDOM = new Random();

    @Autowired
    private static PhoneService PHONE_SERVICE;
    @Autowired
    private static NotebookService NOTEBOOK_SERVICE;
    @Autowired
    private static SmartwatchService  SMARTWATCH_SERVICE;

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