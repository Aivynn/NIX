package com.Command;

import com.model.*;
import com.service.ProductService;
import com.util.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Update implements Command {
    @Autowired
    private static ProductService<Phone> PHONE_SERVICE;
    @Autowired
    private static ProductService<Notebook> NOTEBOOK_SERVICE;
    @Autowired
    private static ProductService<Smartwatch> SMARTWATCH_SERVICE;

    @Override
    public void execute() {
        System.out.println("What do you want to update:");

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

        List<Product> products = new ArrayList<>();
        switch (values[userType]) {
            case PHONE -> PHONE_SERVICE.printAll();
            case NOTEBOOK -> NOTEBOOK_SERVICE.printAll();
            case SMARTWATCH -> SMARTWATCH_SERVICE.printAll();
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
            }
        }
        switch (values[userType]) {
            case PHONE -> products.addAll(PHONE_SERVICE.getAll());
            case NOTEBOOK -> products.addAll(NOTEBOOK_SERVICE.getAll());
            case SMARTWATCH -> products.addAll(SMARTWATCH_SERVICE.getAll());
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
            }
        }
        System.out.println("Enter the number of the element's price should be changed");
        final int number = SCANNER.nextInt();
        System.out.println("Enter new price for element ");
        final int price = SCANNER.nextInt();
        switch (values[userType]) {
            case PHONE -> PHONE_SERVICE.update((Phone) products.get(number),price);
            case SMARTWATCH -> SMARTWATCH_SERVICE.update((Smartwatch) products.get(number),price);
            case NOTEBOOK -> NOTEBOOK_SERVICE.update((Notebook) products.get(number),price);
            default -> throw new IllegalStateException("Illegal argument " + values[number]);
        }
    }
}
