package com.service;

import com.model.Manufacturer;
import com.model.Smartwatch;
import com.repository.JDBC.SmartwatchJDBCRepository;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Singleton
public class SmartwatchService extends ProductService<Smartwatch> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartwatchService.class);
    private static final Random RANDOM = new Random();
    private final SmartwatchJDBCRepository repository;

    private static SmartwatchService instance;

    @Autowired
    public SmartwatchService(SmartwatchJDBCRepository repository) {
        super(repository);
        this.repository = repository;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
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


    @Override
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
    public Smartwatch createFromObject(Smartwatch smartwatch) {
        return null;
    }

    @Override
    public void update(Smartwatch smartwatch, double price) {
        smartwatch.setPrice(price);
        repository.update(smartwatch);
        LOGGER.info("Notebook {} has been deleted", smartwatch.getId());
    }

    public static Smartwatch createSmartwatch(Map<String, Object> x) {
        return new Smartwatch.SmartwatchBuilder((Double) x.get("price"),
                ((Manufacturer) x.get("manufacturer")), LocalDateTime.now())
                .count((Integer) x.get("count"))
                .title((String) x.get("title"))
                .model((String) x.get("model")).build();
    }
}