package examples;

import examples.service.NotifiableProductService;
import examples.service.ProductBundleService;
import examples.repistory.NotifiableProductRepository;
import examples.repistory.ProductBundleRepository;

public class Main {

    private final static ProductBundleService PRODUCT_BUNDLE_SERVICE = new ProductBundleService(new ProductBundleRepository());
    private final static NotifiableProductService NOTIFIABLE_PRODUCT_SERVICE = new NotifiableProductService(new NotifiableProductRepository());

    public static void main(String[] args) {

        PRODUCT_BUNDLE_SERVICE.generateRandomProduct(3);
        NOTIFIABLE_PRODUCT_SERVICE.generateRandomProduct(3);
        NOTIFIABLE_PRODUCT_SERVICE.generateRandomProduct(3);
        System.out.println("Notifications sent: " + NOTIFIABLE_PRODUCT_SERVICE.filterNotifiableProductsAndSendNotifications());

    }
}
