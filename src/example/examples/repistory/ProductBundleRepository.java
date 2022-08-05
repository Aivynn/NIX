package examples.repistory;

import examples.model.ProductBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductBundleRepository implements Repository<ProductBundle> {
    private final Map<Long, ProductBundle> storage = new HashMap<>();


    @Override
    public void save(ProductBundle product) {
        storage.put(product.getId(), product);
    }

    @Override
    public List<ProductBundle> getAll() {
        return new ArrayList<>(storage.values());
    }
}
