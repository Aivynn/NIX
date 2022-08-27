package com.repository.hibernate;

import com.model.DAO.ProductDAO;

public interface CrudRepositoryDAO <T extends ProductDAO> {


    public void save(T product);
    public void update(T product);
    public void delete(String id);
    public T getById(String id);

}
