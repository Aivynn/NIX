package com.repository.hibernate;

import com.model.DAO.ProductDAO;

import java.util.List;

public interface CrudRepositoryDAO <T extends ProductDAO> {


    public void save(T product);
    public void update(String id);
    public void delete(String id);
    public T getById(String id);

    public void saveAll(List<T> products);

}
