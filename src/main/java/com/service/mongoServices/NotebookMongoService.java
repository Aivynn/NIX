package com.service.mongoServices;

import com.model.Manufacturer;
import com.model.Notebook;
import com.repository.mongo.NotebookMongoRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Singleton
public class NotebookMongoService extends ProductMongoService<Notebook>{
    private static final Random RANDOM = new Random();

    private NotebookMongoRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookMongoService.class);

    private static PhoneMongoService instance;

    @Autowired
    public NotebookMongoService(NotebookMongoRepository repository) {
        super(repository);
        this.repository = repository;

    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

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
    public void update(String id) {
        repository.update(id);
        LOGGER.info("Notebook {} has been updated", id);
    }

    @Override
    public Notebook findById(String id){
       return repository.getById(id);
    }
}
