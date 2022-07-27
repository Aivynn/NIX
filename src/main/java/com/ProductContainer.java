package com;

import com.model.Product;

import java.util.Random;

public class ProductContainer<T extends Product> {

    private final Random random = new Random();
    private final T product;
    ProductContainer(T product){
        this.product = product;
    }

    public void applyDiscount() {
        if(product == null) {
            throw new IllegalArgumentException("No such product, try again");
        }
        double currentPrice = product.getPrice();
        double discount = random.nextInt(10,30);
        product.setPrice(currentPrice - (currentPrice * (discount / 100)) );
    }

    public void additionalProducts(int products) {
        if(product == null) {
            throw new IllegalArgumentException("No such product, try again");
        }
        product.setCount(product.getCount() + products);
    }
}
