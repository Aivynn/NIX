package com.Command;

import com.model.Notebook;
import com.model.Phone;
import com.model.ProductType;
import com.model.Smartwatch;
import com.service.ProductService;
import com.util.Autowired;

public class Delete implements Command {

    @Autowired
    private static ProductService<Phone> PHONE_SERVICE;
    @Autowired
    private static ProductService<Notebook> NOTEBOOK_SERVICE;
    @Autowired
    private static ProductService<Smartwatch> SMARTWATCH_SERVICE;

    @Override
    public void execute() {
        System.out.println("What do you want to delete? ");
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
        System.out.println("Enter the id of the element should be deleted");
        String input = SCANNER.next();
        switch (values[userType]) {
            case PHONE -> PHONE_SERVICE.delete(input);
            case NOTEBOOK -> NOTEBOOK_SERVICE.delete(input);
            case SMARTWATCH -> SMARTWATCH_SERVICE.delete(input);
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
            }
        }
    }
}
