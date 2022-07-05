package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.repository.NotebookRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
            System.out.println(notebook); // TODO: 02/07/22
        }
    }

    public boolean changePrice(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        Optional<? extends Notebook> notebook = REPOSITORY.findById(id);
        System.out.println(notebook.get());
        notebook.get().setPrice(450);
        System.out.println(notebook.get());
        return true;
    }

    public boolean delete(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        System.out.println("Full list before delete");
        for (Notebook notebook : REPOSITORY.getAll()) {
            System.out.println(notebook);
        }
        System.out.println();
        LOGGER.info("Notebook {} has been deleted", REPOSITORY.findById(id).get());
        System.out.println();
        System.out.println("List after delete");
        REPOSITORY.delete(id);
        for (Notebook notebook : REPOSITORY.getAll()) {
            System.out.println(notebook);
        }
        return true;
    }
}