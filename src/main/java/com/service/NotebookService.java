package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.repository.NotebookRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NotebookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookService.class);
    private static final Random RANDOM = new Random();
    private static final NotebookRepository REPOSITORY = new NotebookRepository();

    public void createAndSaveNotebooks(int count) {
        List<Notebook> notebooks = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Notebook notebook = (new Notebook(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextInt(1000),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            ));
            notebooks.add(notebook);
            LOGGER.info("Notebook {} has been created", notebook.getId());
        }
        REPOSITORY.saveAll(notebooks);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Notebook notebook : REPOSITORY.getAll()) {
            System.out.println(notebook);
        }
    }

    public boolean changePrice(String id) {
        return REPOSITORY.findById(id).map(notebook -> {
            LOGGER.info("{}", notebook);
            notebook.setPrice(RANDOM.nextInt(1000));
            LOGGER.info("{}", notebook);
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return REPOSITORY.findById(id).map(notebook -> {
            LOGGER.info("{}", REPOSITORY.getAll());
            REPOSITORY.delete(id);
            LOGGER.info("{}, has been deleted", notebook);
            LOGGER.info("{}", REPOSITORY.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }
        /*Optional<Notebook> notebook = REPOSITORY.findById(id);
        if (notebook.isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        System.out.println("Full list before delete");
        for (Notebook notebooks : REPOSITORY.getAll()) {
            System.out.println(notebooks);
        }
        System.out.println();
        LOGGER.info("Notebook {} has been deleted", notebook.get());
        System.out.println();
        System.out.println("List after delete");
        REPOSITORY.delete(id);
        for (Notebook notebooks : REPOSITORY.getAll()) {
            System.out.println(notebooks);
        }
        return true;
    }*/
}