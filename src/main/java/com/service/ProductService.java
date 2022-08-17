package com.service;


import com.model.*;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.model.ProductType.*;

public abstract class ProductService<T extends Product> {

    int i = 0;
    private final CrudRepository<T> repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    public ProductService(CrudRepository<T> repository) {
        this.repository = repository;
        i++;

    }

    public void createAndSaveProducts(int count) {

        if (count <= 0) {
            throw new IllegalArgumentException("Count can't be less then zero");
        }
        List<T> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T phone = createProduct();
            products.add(phone);
            LOGGER.info("Product {} has been saved", phone.getId());
        }
        repository.saveAll(products);
    }

    protected abstract T createProduct();

    public void save(T product) {
        if (product.getCount() == 0) {
            product.setCount(-1);
        }
        repository.save(product);
    }

    public List<Product> getAll() {
        return (List<Product>) repository.getAll();
    }


    public T findOrCreate(String title) {
        if (title.length() < 5) {
            throw new IllegalArgumentException("Title can't be less then 5 chars");
        }
        return repository.findByTitle(title).orElse(createProduct());
    }

    public Optional<T> upsertPhoneTitle(String id, String title) {
        return repository.findById(id).map(phone -> {
            phone.setTitle(title);
            repository.save(phone);
            return phone;
        }).or(() ->
                java.util.Optional.of(createProduct()));
    }

    public void printAll() {
        for (T phone : repository.getAll()) {
            System.out.println(phone);
        }
    }

    public void delete(String id) {
        repository.delete(id);
        LOGGER.info("Product {} has been updated", id);
    }

    public abstract T createFromObject(T t);

    public abstract void update(T t, double price);


    public void expensivePhone(double price) {
        repository.getAll().stream()
                .filter(phone -> price > phone.getPrice())
                .forEach(phone -> System.out.println(phone.getTitle()));
    }

    public void allPhonePrices() {
        Double price = repository.getAll().stream().mapToDouble(Product::getPrice).sum();
        System.out.println(price);
    }

    public void countPhones() {
        Integer phones = repository.getAll().stream().reduce(0, (accum, x) -> accum + x.getCount(), Integer::sum);
        System.out.println(phones);
    }

    public Map<String, String> collectToMap() {
        return repository.getAll().stream().sorted(Comparator.comparing(Product::getTitle)).distinct()
                .collect(Collectors.toMap(Product::getId, Product::toString));
    }

    public void allPrices() {
        DoubleSummaryStatistics statistics = repository.getAll().stream()
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    public Predicate<Collection<T>> hasPrice = (phone -> phone.stream().map(Product::getPrice).anyMatch(Objects::isNull));


    public Function<Map<String, Object>, ? extends Product> createObject = x -> {
        if (x.get("type") == PHONE) {
            return new Phone((String) x.get("title"), (Integer) x.get("count"), (Double) x.get("price"), (String) x.get("model"), (Manufacturer) x.get("manufacturer"), (List<String>) x.get("details"), new OperationSystem(11,"Android"), LocalDateTime.now());
        }
        if (x.get("type") == NOTEBOOK) {
            return new Notebook((String) x.get("title"), (Integer) x.get("count"), (Double) x.get("price"), (String) x.get("model"), (Manufacturer) x.get("manufacturer"));
        }
        if (x.get("type") == SMARTWATCH) {
            return  new Smartwatch.SmartwatchBuilder((Double) x.get("price"),((Manufacturer) x.get("manufacturer")))
                    .count((Integer) x.get("count"))
                    .title((String) x.get("title"))
                    .model((String) x.get("model")).build();
        }
        throw new IllegalArgumentException("No such type, try again");
    };
}

