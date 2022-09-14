package com.service.mongoServices;

import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.Phone;
import com.model.Smartwatch;
import com.repository.mongo.PhoneMongoRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Singleton
public class PhoneMongoService extends ProductMongoService<Phone>{
    private static final Random RANDOM = new Random();

    private PhoneMongoRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneMongoService.class);

    private static PhoneMongoService instance;

    @Autowired
    public PhoneMongoService(PhoneMongoRepository repository) {
        super(repository);
        this.repository = repository;

    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected Phone createProduct() {
        Phone phone = new Phone(
                Phone.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer(),
                List.of("Something","Something else"),
                new OperationSystem(15,"LINUX"),
                LocalDateTime.now()
        );
        repository.save(phone);
        return phone;
    }


    @Override
    public void update(String id) {
        repository.update(id);
        LOGGER.info("Notebook {} has been updated", id);
    }

    @Override
    public Phone findById(String id){
        return repository.getById(id);
    }
}
