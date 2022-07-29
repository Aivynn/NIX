package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Phone extends Product{
    private final String model;
    private final Manufacturer manufacturer;
    private List<String> details;

    public Phone(String title, int count, double price, String model, Manufacturer manufacturer,List<String> details) {
        super(title, count, price,ProductType.PHONE);
        this.model = model;
        this.details = details;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "manufacturer=" + manufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

}
