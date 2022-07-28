package com.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Smartwatch extends Product {

    private final String model;
    private final Manufacturer manufacturer;

    public Smartwatch(String title, int count, double price, String model, Manufacturer manufacturer) {
        super(title, count, price,ProductType.SMARTWATCH);
        this.model = model;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Smartwatch{" +
                "manufacturer=" + manufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
