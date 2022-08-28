package com.service.DaoServices;

import com.model.DAO.SmartwatchDAO;
import com.model.Manufacturer;
import com.model.Smartwatch;
import com.repository.JDBC.SmartwatchJDBCRepository;
import com.repository.hibernate.SmartwatchDaoRepository;
import com.service.ProductService;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Singleton
public class SmartwatchDaoService extends ProductDaoService<SmartwatchDAO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartwatchDaoService.class);
    private static final Random RANDOM = new Random();
    private SmartwatchDaoRepository repository;

    private static SmartwatchDaoService instance;

    @Autowired
    public SmartwatchDaoService(SmartwatchDaoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }


    protected SmartwatchDAO createProduct() {
        SmartwatchDAO smartwatch = new SmartwatchDAO.SmartwatchBuilder(RANDOM.nextDouble(1000.0), getRandomManufacturer(), LocalDateTime.now())
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
}