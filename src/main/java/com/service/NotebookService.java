package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import com.model.Phone;
import com.repository.CrudRepository;
import com.repository.NotebookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NotebookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookService.class);
    private static final Random RANDOM = new Random();
    private NotebookRepository repository;
    
    public NotebookService(NotebookRepository repository) {
        this.repository = repository;
    }
    
    

    public void createAndSaveNotebooks(int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("Count can't be zero or less");
        }
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
        repository.saveAll(notebooks);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Notebook notebook : repository.getAll()) {
            System.out.println(notebook);
        }
    }

    public boolean changePrice(String id) {
        return repository.findById(id).map(notebook -> {
            LOGGER.info("{}", notebook);
            notebook.setPrice(RANDOM.nextInt(1000));
            LOGGER.info("{}", notebook);
            return repository.update(notebook);
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return repository.findById(id).map(notebook -> {
            LOGGER.info("{}", repository.getAll());
            repository.delete(id);
            LOGGER.info("{}, has been deleted", notebook);
            LOGGER.info("{}", repository.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public List<Notebook> getAll() {
        return repository.getAll();
    }
    public boolean saveNotebook(Notebook notebook) {
        if (notebook.getCount() == 0) {
            notebook.setCount(-1);
        }
        return repository.update(notebook);
    }
}