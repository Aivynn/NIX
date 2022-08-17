package examples.service;

import examples.model.ProductBundle;
import examples.repistory.ProductBundleRepository;

import java.util.Random;

public class ProductBundleService extends ProductService<ProductBundle> {
    private final ProductBundleRepository repository;

    public ProductBundleService(ProductBundleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void generateRandomProduct(int count) {
        int i = 0;
        Random random = new Random();
        while (i < count) {
            ProductBundle product = new ProductBundle();
            product.setAmount(random.nextInt(15));
            product.setAvailable(random.nextBoolean());
            product.setPrice(random.nextDouble());
            product.setId(random.nextLong());
            product.setTitle(random.nextFloat() + "" + random.nextDouble());
            i++;
            repository.save(product);
        }
    }
}
