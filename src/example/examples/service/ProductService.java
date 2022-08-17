package examples.service;

import examples.model.Product;
import examples.repistory.Repository;

public abstract class ProductService<T extends Product> {

    private final Repository<? extends Product> repository;

    public ProductService(Repository<T> repository) {
        this.repository = repository;

    }
    abstract void generateRandomProduct(int count);
}
