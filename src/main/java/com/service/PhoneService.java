package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService {
    private static final Random RANDOM = new Random();
    private static final PhoneRepository REPOSITORY = new PhoneRepository();
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    public void createAndSavePhones(int count) {
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
        REPOSITORY.saveAll(phones);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : REPOSITORY.getAll()) {
            System.out.println(phone); // TODO: 02/07/22
        }
    }

    public boolean changePrice(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        Optional<? extends Phone> phone = REPOSITORY.findById(id);
        System.out.println(phone.get());
        phone.get().setPrice(450);
        System.out.println(phone.get());
        return true;
    }

    public boolean delete(String id) {
        if (REPOSITORY.findById(id).isEmpty()) {
            System.out.println("No such id, try again");
            return false;
        }
        System.out.println("Full list before delete");
        for (Phone phone : REPOSITORY.getAll()) {
            System.out.println(phone);
        }
        System.out.println();
        LOGGER.info("Phone{} has been deleted", REPOSITORY.findById(id).get());
        System.out.println();
        System.out.println("List after delete");
        REPOSITORY.delete(id);
        for (Phone phone : REPOSITORY.getAll()) {
            System.out.println(phone);
        }
        return true;
    }
}