package com.model.DAO;

import com.model.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProductDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    protected String title;
    protected Integer count;
    protected Double price;
    protected final ProductType type;

    @ManyToMany(mappedBy = "product")
    private List<InvoiceDAO> invoiceDAO;


    public ProductDAO(){
        this.type = ProductType.NONE;
    }
    protected ProductDAO(String title, Integer count, Double price, ProductType type) {
        this.title = title;
        this.count = count;
        this.price = price;
        this.type = type;
    }
}
