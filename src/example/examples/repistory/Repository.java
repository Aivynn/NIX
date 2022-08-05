package examples.repistory;

import examples.model.Product;

import java.util.List;

public interface Repository<T extends Product> {
    void save(T t);
    List<T> getAll();
}
