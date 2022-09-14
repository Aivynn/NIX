package com.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notebook extends Product {

    private final String model;
    private final Manufacturer manufacturer;

    private LocalDateTime date;

    private OperationSystem operationSystem;

    public Notebook(){
        super("123213",41,4444.34,ProductType.NOTEBOOK);
        this.model = "123123";
        this.manufacturer = Manufacturer.APPLE;

    }


    public Notebook(String title, int count, double price, String model, Manufacturer manufacturer) {
        super(title, count, price,ProductType.NOTEBOOK);
        this.model = model;
        this.manufacturer = manufacturer;
        date = LocalDateTime.now();
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
