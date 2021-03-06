package com.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notebook extends Product {

    private final String model;
    private final Manufacturer manufacturer;


    public Notebook(String title, int count, double price, String model, Manufacturer manufacturer) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "manufacturer=" + manufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
