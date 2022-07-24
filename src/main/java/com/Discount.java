package com;

import com.model.Product;

import java.util.Random;

public class Discount<T extends Product> {

    private final Random random = new Random();
    Discount(){

    }

    public void getDiscount(T t) {
        if(t == null) {
            throw new IllegalArgumentException("No such product, try again");
        }
        double currentPrice = t.getPrice();
        double discount = random.nextInt(10,30);
        t.setPrice(currentPrice - (currentPrice * (discount / 100)) );
    }

    public void addProducts(T t, int products) {
        if(t == null) {
            throw new IllegalArgumentException("No such product, try again");
        }
        t.setCount(t.getCount() + products);
    }
}
