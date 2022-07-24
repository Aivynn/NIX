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

public class NotebookService extends ProductService<Notebook> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookService.class);
    private static final Random RANDOM = new Random();
    private NotebookRepository repository;

    public NotebookService(NotebookRepository repository) {
        super(repository);
        this.repository = repository;

    }
    @Override
    protected Notebook createProduct() {
        return new Notebook(
                Notebook.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer()
        );
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
            notebook.setPrice((double) RANDOM.nextInt(1000));
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