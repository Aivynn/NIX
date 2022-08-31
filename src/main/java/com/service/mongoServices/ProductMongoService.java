package com.service.mongoServices;

import com.model.Product;
import com.repository.mongo.CrudMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class ProductMongoService<T extends Product> {

    private final CrudMongoRepository<T> repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(com.service.PhoneService.class);

    public ProductMongoService(CrudMongoRepository<T> repository) {
        this.repository = repository;

    }

    public List<T> createAndSaveProducts(int count) {

        if (count <= 0) {
            throw new IllegalArgumentException("Count can't be less then zero");
        }
        List<T> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T phone = createProduct();
            products.add(phone);
            LOGGER.info("Product {} has been saved", phone.getId());
        }
        return products;
    }

    protected abstract T createProduct();


    public void save(T product) {
        if (product.getCount() == 0) {
            product.setCount(-1);
        }
        repository.save(product);
    }


    public void delete(String id) {
        repository.delete(id);
        LOGGER.info("Product {} has been updated", id);
    }


    public abstract void update(String id);
    public abstract T findById(String id);
}
