package com.model;

public class InvoiceData {
    private String id;
    private Double price;

    @Override
    public String toString() {
        return "InvoiceData{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    private int count;
    public InvoiceData(String id, Double price,int count){
        this.id = id;
        this.count = count;
        this.price = price;
    }
}
