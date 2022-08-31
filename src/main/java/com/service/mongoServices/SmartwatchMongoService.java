package com.service.mongoServices;

import com.model.Manufacturer;
import com.model.Notebook;
import com.model.Smartwatch;
import com.repository.mongo.SmartwatchMongoRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;

@Singleton
public class SmartwatchMongoService extends ProductMongoService<Smartwatch> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartwatchMongoService.class);
    private static final Random RANDOM = new Random();
    private SmartwatchMongoRepository repository;

    private static SmartwatchMongoService instance;

    @Autowired
    public SmartwatchMongoService(SmartwatchMongoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }


    protected Smartwatch createProduct() {
        Smartwatch smartwatch = new Smartwatch.SmartwatchBuilder(RANDOM.nextDouble(1000.0), getRandomManufacturer(), LocalDateTime.now())
                .title(Smartwatch.class.getSimpleName() + "-" + RANDOM.nextInt(1000))
                .count(RANDOM.nextInt(500))
                .model("Model-" + RANDOM.nextInt(10))
                .build();
        repository.save(smartwatch);
        return smartwatch;


    }


    @Override
    public void update(String id) {
        repository.update(id);
        LOGGER.info("Notebook {} has been deleted", id);
    }

    @Override
    public Smartwatch findById(String id){
        return repository.getById(id);
    }
}
