package com.service.DaoServices;

import com.model.DAO.PhoneDAO;
import com.model.DAO.ProductDAO;
import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.Phone;
import com.repository.JDBC.PhoneJDBCRepository;
import com.repository.PhoneRepository;
import com.repository.hibernate.PhoneDaoRepository;
import com.service.ProductService;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Singleton
public class PhoneDaoService extends ProductDaoService<PhoneDAO> {

    private static final Random RANDOM = new Random();

    private PhoneDaoRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDaoService.class);

    private static PhoneDaoService instance;

    @Autowired
    public PhoneDaoService(PhoneDaoRepository repository) {
        super(repository);
        this.repository = repository;

    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected PhoneDAO createProduct() {
        PhoneDAO phone = new PhoneDAO(
                Phone.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer(),
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



}