package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService {
    private static final Random RANDOM = new Random();
    private final PhoneRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    public PhoneService(PhoneRepository repository) {
        this.repository = repository;

    }

    public void createAndSavePhones(int count) {

        if(count <= 0) {
            throw new IllegalArgumentException("Count can't be less then zero");
        }
        List<Phone> phones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Phone phone = (new Phone(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextInt(1000),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            ));
            phones.add(phone);
            LOGGER.info("Phone {} has been created", phone.getId());
        }
        repository.saveAll(phones);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : repository.getAll()) {
            System.out.println(phone);
        }
    }

    public boolean changePrice(String id) {
        return repository.findById(id).map(phone -> {
            LOGGER.info("{}", phone);
            phone.setPrice(RANDOM.nextInt(1000));
            repository.update(phone);
            LOGGER.info("{}", phone);
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return repository.findById(id).map(phone -> {
            LOGGER.info("{}", repository.getAll());
            repository.delete(id);
            LOGGER.info("{}, has been deleted", phone);
            LOGGER.info("{}", repository.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public List<Phone> getAll() {
        return repository.getAll();
    }

    public boolean savePhone(Phone phone) {
        if (phone.getCount() == 0) {
            phone.setCount(-1);
        }
       return repository.update(phone);
    }
    public boolean updateTitle(Phone phone,String title) {
        if(title.length() > 3){
            phone.setTitle(title);
           return repository.update(phone);
        }
        return false;
    }
}