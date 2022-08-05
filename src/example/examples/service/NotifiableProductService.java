package examples.service;

import examples.model.NotifiableProduct;
import examples.repistory.NotifiableProductRepository;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NotifiableProductService extends ProductService<NotifiableProduct> implements NotificationSender {
    private final NotifiableProductRepository repository;

    public NotifiableProductService(NotifiableProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void generateRandomProduct(int count) {
        int i = 0;
        Random random = new Random();
        while (i < count) {
            NotifiableProduct product = new NotifiableProduct();
            product.setChannel(random.nextBoolean() + "" + random.nextDouble());
            product.setAvailable(random.nextBoolean());
            product.setPrice(random.nextDouble());
            product.setId(random.nextLong());
            product.setTitle(random.nextFloat() + "" + random.nextDouble());
            i++;
            repository.save(product);
        }
    }

    @Override
    public int filterNotifiableProductsAndSendNotifications() {
        AtomicInteger notification = new AtomicInteger();
        repository.getAll().forEach(x -> {
            notification.getAndIncrement();
            //doing something cool
        });
        return notification.get();
    }
}
