package com.service;

import com.model.Manufacturer;
import com.model.Smartwatch;
import com.repository.SmartwatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SmartwatchService {
    private static final Random RANDOM = new Random();
    private static final SmartwatchRepository REPOSITORY = new SmartwatchRepository();
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartwatchService.class);

    public void createAndSaveSmartwatches(int count) {
        List<Smartwatch> smartwatches = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Smartwatch smartwatch = new Smartwatch(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextInt(1000),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            );
            smartwatches.add(smartwatch);
            LOGGER.info("Smartwatch {} has been created", smartwatch.getId());
        }
        REPOSITORY.saveAll(smartwatches);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Smartwatch smartwatch : REPOSITORY.getAll()) {
            System.out.println(smartwatch); // TODO: 02/07/22
        }
    }

    public boolean changePrice(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        Optional<? extends Smartwatch> smartwatch = REPOSITORY.findById(id);
        System.out.println(smartwatch.get());
        smartwatch.get().setPrice(450);
        System.out.println(smartwatch.get());
        return true;
    }

    public boolean delete(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        System.out.println("Full list before delete");
        for (Smartwatch smartwatch : REPOSITORY.getAll()) {
            System.out.println(smartwatch);
        }
        System.out.println();
        LOGGER.info("Smartwatch {} has been deleted", REPOSITORY.findById(id).get());
        System.out.println();
        System.out.println("List after delete");
        REPOSITORY.delete(id);
        for (Smartwatch smartwatch : REPOSITORY.getAll()) {
            System.out.println(smartwatch);
        }
        return true;
    }
}