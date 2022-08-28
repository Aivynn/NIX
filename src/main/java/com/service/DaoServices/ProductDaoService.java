package com.service.DaoServices;


import com.model.DAO.ProductDAO;
import com.model.Product;
import com.repository.CrudRepository;
import com.repository.hibernate.CrudRepositoryDAO;
import com.service.NotebookService;
import com.service.PhoneService;
import com.service.SmartwatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.model.ProductType.*;

public abstract class ProductDaoService<T extends ProductDAO> {

    private final CrudRepositoryDAO<T> repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(com.service.PhoneService.class);

    public ProductDaoService(CrudRepositoryDAO<T> repository) {
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

}

