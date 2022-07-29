package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.model.Product;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhoneService extends ProductService<Phone> {

    private static final Random RANDOM = new Random();
    private final PhoneRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private static PhoneService instance;

    public PhoneService(PhoneRepository repository) {
        super(repository);
        this.repository = repository;

    }

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(PhoneRepository.getInstance());
        }
        return instance;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changePrice(String id) {
        return repository.findById(id).map(phone -> {
            LOGGER.info("{}", phone);
            phone.setPrice((double) RANDOM.nextInt(1000));
            repository.update(phone);
            LOGGER.info("{}", phone);
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    @Override
    protected Phone createProduct() {
        return new Phone(
                Phone.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer(),
                Stream.of("foo", "bar")
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void update(Phone phone,double price) {
        phone.setPrice(price);
        repository.update(phone);
        LOGGER.info("Notebook {} has been updated", phone.getId());
    }

    public boolean updateTitle(Phone phone, String title) {
        if (title.length() > 3) {
            phone.setTitle(title);
            return repository.update(phone);
        }
        return false;
    }

    public void expensivePhone(double price) {
        repository.getAll().stream()
                .filter(phone -> price > phone.getPrice())
                .forEach(phone -> System.out.println(phone.getTitle()));
    }

    public void allPhonePrices() {
       Double price = repository.getAll().stream().mapToDouble(Product::getPrice).sum();
       System.out.println(price);
    }

    public void sortByTitle() {
        List<Phone> phones = repository.getAll().stream().sorted().toList();
        HashMap<String, String> map = (HashMap<String, String>) phones.stream()
                .collect(Collectors.toMap(Phone::getId,Phone::getTitle));
    }
    public void allPrices() {
        repository.getAll()
                .forEach(phone -> System.out.println(phone.getPrice()));
    }

    public Optional<Boolean> findAny(String detail) {
        return repository.getAll()
                .stream()
                .flatMap(phone -> phone.getDetails().stream().map(s -> Objects.equals(s, detail)))
                .findAny();

    }
}