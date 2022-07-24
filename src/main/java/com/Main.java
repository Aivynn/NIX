package com;


import java.util.Scanner;

import com.repository.NotebookRepository;
import com.repository.PhoneRepository;
import com.repository.SmartwatchRepository;
import com.service.NotebookService;
import com.service.PhoneService;
import com.service.SmartwatchService;


public class Main {

    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final NotebookService NOTEBOOK_SERVICE = new NotebookService(new NotebookRepository());
    private static final SmartwatchService SMARTWATCH_SERVICE = new SmartwatchService(new SmartwatchRepository());

    public static void main(String[] args) {
        NOTEBOOK_SERVICE.createAndSaveProducts(5);
        PHONE_SERVICE.createAndSaveProducts(5);
        SMARTWATCH_SERVICE.createAndSaveProducts(5);
        System.out.println("Enter the name of product you want to change,1 - Notebook, 2 - Phone, 3 - Smartwatch ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            switch (Integer.parseInt(name)) {
                case 1:
                    System.out.println("Enter the id of notebook you want to change: ");
                    String id = scanner.nextLine();
                    NOTEBOOK_SERVICE.changePrice(id);
                    break;
                case 2:
                    System.out.println("Enter the id of phone you want to change: ");
                    id = scanner.nextLine();
                    PHONE_SERVICE.changePrice(id);
                    break;
                case 3:
                    System.out.println("Enter the id of smartwatch you want to change: ");
                    id = scanner.nextLine();
                    SMARTWATCH_SERVICE.changePrice(id);
                    break;
                default:
                    System.out.println("No such product, try again");
            }
            System.out.println("Enter the name of product you want to delete ,1 - Notebook, 2 - Phone, 3 - Smartwatch ");
            name = scanner.nextLine();
            switch (Integer.parseInt(name)) {
                case 1:
                    System.out.println("Enter the id of notebook you want to delete: ");
                    String id = scanner.nextLine();
                    NOTEBOOK_SERVICE.delete(id);
                    break;
                case 2:
                    System.out.println("Enter the id of notebook you want to delete: ");
                    id = scanner.nextLine();
                    PHONE_SERVICE.delete(id);
                    break;
                case 3:
                    System.out.println("Enter the id of notebook you want to delete: ");
                    id = scanner.nextLine();
                    SMARTWATCH_SERVICE.delete(id);
                    break;
                default:
                    System.out.println("No such product, try again");
            }
        }
        catch (Exception E) {
            System.out.println("Something went wrong, try again");
        }
    }
}
