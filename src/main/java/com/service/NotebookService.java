package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import com.repository.JDBC.NotebookJDBCRepository;
import com.repository.NotebookRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Singleton
public class NotebookService extends ProductService<Notebook> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookService.class);
    private static final Random RANDOM = new Random();
    private final NotebookJDBCRepository repository = new NotebookJDBCRepository();

    private static NotebookService instance;

    @Autowired
    public NotebookService(NotebookRepository repository) {
        super(repository);

    }

    public static NotebookService getInstance() {
        if (instance == null) {
            instance = new NotebookService(NotebookRepository.getInstance());
        }
        return instance;
    }

    @Override
    protected Notebook createProduct() {
        Notebook notebook = new Notebook(
                Notebook.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer()
        );
        repository.save(notebook);
        return notebook;
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

    public static Notebook createNotebook(Map<String, Object> x){
        return new Notebook((String) x.get("title"),
                (Integer) x.get("count"),
                (Double) x.get("price"),
                (String) x.get("model"),
                (Manufacturer) x.get("manufacturer"));

    }

    public List<Notebook> findProducts(int limit){
        return repository.getAllWithLimit(limit);
    }


}