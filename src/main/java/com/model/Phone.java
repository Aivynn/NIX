package com.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Phone extends Product{
    private final String model;
    private final Manufacturer manufacturer;
    private List<String> details;

    private LocalDateTime date;

    private OperationSystem operationSystem;

    public Phone(String title, int count, double price, String model, Manufacturer manufacturer,List<String> details,OperationSystem operationSystem,LocalDateTime date) {
        super(title, count, price,ProductType.PHONE);
        this.model = model;
        this.details = details;
        this.manufacturer = manufacturer;
        this.operationSystem = operationSystem;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "manufacturer=" + manufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", date=" + date +
                ", details=" + details +
                ", operationsystem=" + operationSystem +
                '}';
    }

}
