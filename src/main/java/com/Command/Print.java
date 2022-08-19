package com.Command;

import com.model.Notebook;
import com.model.Phone;
import com.model.ProductType;
import com.model.Smartwatch;
import com.service.NotebookService;
import com.service.PhoneService;
import com.service.ProductService;
import com.service.SmartwatchService;
import com.util.Autowired;


public class Print implements Command{
    @Autowired
    private static  PhoneService PHONE_SERVICE;
    @Autowired
    private static NotebookService NOTEBOOK_SERVICE;
    @Autowired
    private static  SmartwatchService SMARTWATCH_SERVICE;
    @Override
    public void execute() {
        System.out.println("What do you want to print? ");
        final ProductType[] values = ProductType.values();
        int userType = -1;
        do {
            for (int i = 0; i < values.length; i++) {
                System.out.printf("%d) %s%n", i, values[i].name());
            }
            int input = SCANNER.nextInt();
            if (input >= 0 && input < values.length) {
                userType = input;
            }
        } while (userType == -1);

        switch (values[userType]) {
            case PHONE -> PHONE_SERVICE.printAll();
            case NOTEBOOK -> NOTEBOOK_SERVICE.printAll();
            case SMARTWATCH -> SMARTWATCH_SERVICE.printAll();
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
            }
        }
     }
    }
