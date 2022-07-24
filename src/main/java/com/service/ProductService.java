package com.service;

import com.model.Phone;
import com.model.Product;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class ProductService<T extends Product> {
    private static final Random RANDOM = new Random();
    private final CrudRepository<T> repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    public ProductService(CrudRepository repository) {
        this.repository = repository;

    }

    public void createAndSaveProducts(int count) {

        if(count <= 0) {
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
    public List<T> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (T phone : repository.getAll()) {
            System.out.println(phone);
        }
    }

    public T findOrCreate(String title) {
        if(title.length() < 5) {
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

}