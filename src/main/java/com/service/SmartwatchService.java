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
        return REPOSITORY.findById(id).map(smartwatch -> {
            LOGGER.info("{}", smartwatch);
            smartwatch.setPrice(RANDOM.nextInt(1000));
            LOGGER.info("{}", smartwatch);
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return REPOSITORY.findById(id).map(smartwatch -> {
            LOGGER.info("{}", REPOSITORY.getAll());
            REPOSITORY.delete(id);
            LOGGER.info("{}, has been deleted", smartwatch);
            LOGGER.info("{}", REPOSITORY.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }
}