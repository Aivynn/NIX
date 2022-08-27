package com.model.DAO;

import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class PhoneDAO extends ProductDAO {
    private String model;
    private Manufacturer manufacturer;
    private LocalDateTime date;


    public PhoneDAO(){

    }

    public PhoneDAO(String title, int count, double price, String model, Manufacturer manufacturer, LocalDateTime date) {
        super(title, count, price, ProductType.PHONE);
        this.model = model;
        this.manufacturer = manufacturer;
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
                '}';
    }

}
