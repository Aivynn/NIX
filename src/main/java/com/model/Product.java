package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Product {
    protected final String id;
    protected String title;
    protected Integer count;
    protected Double price;
    protected final ProductType type;

    protected Product(String title, Integer count, Double price,ProductType type) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
        this.type = type;
    }
}
