package com.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class Invoice {
    private String id;
    private double sum;
    private List<? extends Product> products;
    private LocalDateTime time;

    public Invoice(){
        this.products = new ArrayList<>();
    }

    private Invoice(InvoiceBuilder invoiceBuilder) {
        this.products = invoiceBuilder.products;
        id = invoiceBuilder.id;
        this.sum = invoiceBuilder.sum;
        this.time = invoiceBuilder.time;

    }

    public static class InvoiceBuilder {
        protected double sum;
        protected List<? extends Product> products;
        protected LocalDateTime time;
        protected String id;

        public InvoiceBuilder sum(double sum){
            this.sum = sum;
            return this;
        }

        public InvoiceBuilder id(String id){
            this.id = id;
            return this;
        }
        public InvoiceBuilder products(List<? extends Product> products){
            this.products = products;
            return this;
        }
        public InvoiceBuilder time(LocalDateTime time){
            this.time = time;
            return this;
        }

        public Invoice build(){
            return new Invoice(this);
        }


    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' + "\n" +
                "sum=" + sum + "\n" +
                "products=" + products + "\n" +
                "time=" + time +
                '}';
    }
}