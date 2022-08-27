package com.model.DAO;


import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class NotebookDAO extends ProductDAO {

    private String model;
    private Manufacturer manufacturer;

    private LocalDateTime date;


    public NotebookDAO(){

    }
    public NotebookDAO(String title, int count, double price, String model, Manufacturer manufacturer) {
        super(title, count, price, ProductType.NOTEBOOK);
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
