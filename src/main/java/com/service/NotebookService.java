package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import com.model.Phone;
import com.repository.CrudRepository;
import com.repository.NotebookRepository;
import com.repository.PhoneRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public class NotebookService extends ProductService<Notebook> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookService.class);
    private static final Random RANDOM = new Random();
    private NotebookRepository repository;

    private static NotebookService instance;

    @Autowired
    public NotebookService(NotebookRepository repository) {
        super(repository);
        this.repository = repository;

    }

    public static NotebookService getInstance() {
        if (instance == null) {
            instance = new NotebookService(NotebookRepository.getInstance());
        }
        return instance;
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

    @Override
    public Notebook createFromObject(Notebook notebook) {
        return null;
    }

    @Override
    public void update(Notebook notebook, double price) {
        notebook.setPrice(price);
        repository.update(notebook);
        LOGGER.info("Notebook {} has been updated", notebook.getId());
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
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
}