package com.Command;

import com.model.*;
import com.service.NotebookService;
import com.service.PhoneService;
import com.service.ProductService;
import com.service.SmartwatchService;
import com.util.ReadFromXMLFIle;
import com.util.ReaderFromJsonFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class CreateObject implements Command {

    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<Notebook> NOTEBOOK_SERVICE = NotebookService.getInstance();
    private static final ProductService<Smartwatch> SMARTWATCH_SERVICE = SmartwatchService.getInstance();
    @Override
    public void execute() throws IOException, URISyntaxException {
        System.out.println("What type of file you want to read?");
        final FileTypes[] values = FileTypes.values();
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

        Reader reader = null;
        if (userType == 0) {
            reader = new ReaderFromJsonFile();
        }
        if (userType == 1) {
            reader = new ReadFromXMLFIle();
        }
        HashMap<String, String> map = reader.parserToProduct();
        Manufacturer manufacturer;
        String date = "2022-07-25T18:58:23Z"; //2022-07-25T18:58:23Z
        LocalDateTime time = LocalDateTime.parse(map.get("created"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        if (Objects.equals(map.get("manufacturer"), "SAMSUNG")) {
            manufacturer = Manufacturer.SAMSUNG;
        } else if (Objects.equals(map.get("manufacturer"), "SONY")) {
            manufacturer = Manufacturer.SONY;
        } else if (Objects.equals(map.get("manufacturer"), "SAMSUNG")) {
            manufacturer = Manufacturer.APPLE;
        } else {
            throw new IllegalStateException("Unknown manufacturer, try again");
        }

        final ProductType[] productTypes = ProductType.values();
        do {
            for (int i = 0; i < productTypes.length; i++) {
                System.out.printf("%d) %s%n", i, productTypes[i].name());
            }
            int input = SCANNER.nextInt();
            if (input >= 0 && input < values.length) {
                userType = input;
            }
        } while (userType == -1);
        switch (productTypes[userType]) {
            case PHONE -> PHONE_SERVICE.save(new Phone(map.get("title"),
                    Integer.parseInt(map.get("count")),
                    Double.parseDouble((map.get("price").replaceAll("[^0-9?!\\.]", ""))),
                    map.get("model"),
                    manufacturer,
                    Stream.of("a", "b").toList(),
                    new OperationSystem((Integer.parseInt(map.get("version"))), map.get("designation")),
                    time));
            case SMARTWATCH -> SMARTWATCH_SERVICE.save(new Smartwatch(map.get("title"),
                    Integer.parseInt(map.get("count")),
                    Double.parseDouble((map.get("price").replaceAll("[^0-9?!\\.]", ""))),
                    map.get("model"),
                    manufacturer));
            case NOTEBOOK -> NOTEBOOK_SERVICE.save(new Notebook(map.get("title"),
                    Integer.parseInt(map.get("count")),
                    Double.parseDouble((map.get("price").replaceAll("[^0-9?!\\.]", ""))),
                    map.get("model"),
                    manufacturer));
            default -> {
                throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
            }
        }
    }
}
