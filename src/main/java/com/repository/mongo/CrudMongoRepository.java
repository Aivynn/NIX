package com.repository.mongo;

import com.model.Product;

public interface CrudMongoRepository<T extends Product> {
    public void save(T product);
    public void update(String id);
    public void delete(String id);
    public T getById(String id);
}
