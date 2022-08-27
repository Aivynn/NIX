package com.model.DAO;

import com.model.Product;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity(name = "Invoice")
@Getter
public class InvoiceDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private double sum;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Invoice_products", // Name of linking table
            joinColumns = { @JoinColumn(name = "invoice_id") }, // Foreign key to owner entity
            inverseJoinColumns = { @JoinColumn(name = "product_id") }  // Foreign key to mappedBy side
    )
    private List<ProductDAO> product = new ArrayList<>();
    private LocalDateTime time;

    public InvoiceDAO() {
    }

    private InvoiceDAO(InvoiceBuilder invoiceBuilder) {
        this.product = invoiceBuilder.products;
        id = invoiceBuilder.id;
        this.sum = invoiceBuilder.sum;
        this.time = invoiceBuilder.time;

    }

    public static class InvoiceBuilder {
        protected double sum;
        protected List<ProductDAO> products;
        protected LocalDateTime time;
        protected String id;

        public InvoiceBuilder sum(double sum) {
            this.sum = sum;
            return this;
        }

        public InvoiceBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InvoiceBuilder products(List<ProductDAO> products) {
            this.products = products;
            return this;
        }

        public InvoiceBuilder time(LocalDateTime time) {
            this.time = time;
            return this;
        }


        public InvoiceDAO build() {
            InvoiceDAO invoiceDAO = new InvoiceDAO(this);
            return invoiceDAO;
        }


    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' + "\n" +
                "sum=" + sum + "\n" +
                //"products=" + products + "\n" +
                "time=" + time +
                '}';
    }
}