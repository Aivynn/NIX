package examples.repistory;


import examples.model.NotifiableProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifiableProductRepository implements Repository<NotifiableProduct> {
    private final Map<Long, NotifiableProduct> storage = new HashMap<>();


    @Override
    public void save(NotifiableProduct product) {
        storage.put(product.getId(), product);
    }

    @Override
    public List<NotifiableProduct> getAll() {
        return new ArrayList<>(storage.values());
    }
}
