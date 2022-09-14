package com.model.mongoInvoice;

import com.model.Notebook;
import com.model.Phone;
import com.model.Product;
import com.model.Smartwatch;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class InvoiceMongo {

    private String id;
    private final double sum;

    private final List<Phone> phones;

    private final List<Notebook> notebooks;

    private final List<Smartwatch> smartwatches;
    private final LocalDateTime time;

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.addAll(phones);
        products.addAll(notebooks);
        products.addAll(smartwatches);
        return products;
    }

    private InvoiceMongo(InvoiceMongo.InvoiceBuilder invoiceBuilder) {
        this.phones = invoiceBuilder.phones;
        this.notebooks = invoiceBuilder.notebooks;
        this.smartwatches = invoiceBuilder.smartwatches;
        id = invoiceBuilder.id;
        this.sum = invoiceBuilder.sum;
        this.time = invoiceBuilder.time;

    }

    public static class InvoiceBuilder {
        protected double sum;
        protected LocalDateTime time;
        protected String id;
        protected List<Product> products;
        private List<Phone> phones = new ArrayList<>();

        private List<Notebook> notebooks = new ArrayList<>();

        private List<Smartwatch> smartwatches = new ArrayList<>();


        public InvoiceMongo.InvoiceBuilder sum(double sum) {
            this.sum = sum;
            return this;
        }

        public InvoiceMongo.InvoiceBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InvoiceMongo.InvoiceBuilder time(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public InvoiceMongo.InvoiceBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public InvoiceMongo build() {
            InvoiceMongo invoiceMongo = new InvoiceMongo(this);
            validate();
            return invoiceMongo;
        }

        public void validate() {
            products.forEach(x -> {
                switch (x.getType()) {
                    case PHONE -> phones.add((Phone) x);
                    case NOTEBOOK -> notebooks.add((Notebook) x);
                    case SMARTWATCH -> smartwatches.add((Smartwatch) x);
                    default -> throw new IllegalArgumentException("Unknown Product type: " + x.getType());
                }
                ;
            });
        }


    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' + "\n" +
                "products= " + this.getProducts() + "\n" +
                "sum=" + sum + "\n" +
                "time=" + time +
                '}';
    }
}
