package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import com.model.Phone;
import com.model.Smartwatch;
import com.repository.SmartwatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SmartwatchService extends ProductService<Smartwatch> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartwatchService.class);
    private static final Random RANDOM = new Random();
    private SmartwatchRepository repository;

    public SmartwatchService(SmartwatchRepository repository) {
        super(repository);
        this.repository = repository;
    }




    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Smartwatch smartwatch : repository.getAll()) {
            System.out.println(smartwatch);
        }
    }

    public boolean changePrice(String id) {
        return repository.findById(id).map(smartwatch -> {
            LOGGER.info("{}", smartwatch);
            smartwatch.setPrice((double) RANDOM.nextInt(1000));
            LOGGER.info("{}", smartwatch);
            return repository.update(smartwatch);
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return repository.findById(id).map(smartwatch -> {
            LOGGER.info("{}", repository.getAll());
            repository.delete(id);
            LOGGER.info("{}, has been deleted", smartwatch);
            LOGGER.info("{}", repository.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    @Override
    protected Smartwatch createProduct() {
        return new Smartwatch(
                Smartwatch.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer()
        );
    }

    public List<Smartwatch> getAll() {
        return repository.getAll();
    }
    public boolean saveSmartwatch(Smartwatch smartwatch) {
        if (smartwatch.getCount() == 0) {
            smartwatch.setCount(-1);
        }
        return repository.update(smartwatch);
    }
}