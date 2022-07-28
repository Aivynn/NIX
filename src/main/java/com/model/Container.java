package com.model;

import java.util.Random;

public class Container<T extends Product> {

    private final T product;

    public Container(T product) {
        this.product = product;
    }

    public void applyDiscount() {
        Random random = new Random();
        int discount = random.nextInt(10,30);

        product.setPrice(product.getPrice() - (product.getPrice() * discount / 100));
    }

    public void additionalProducts(Number count) {
        product.setCount(product.getCount() + count.intValue());
    }
}
