package com.model.DAO;

import com.model.Manufacturer;
import com.model.ProductType;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class SmartwatchDAO extends ProductDAO {

    private String model;
    private Manufacturer manufacturer;

    protected LocalDateTime date;

    public SmartwatchDAO(){

    }
    private SmartwatchDAO(SmartwatchBuilder smartwatchBuilder) {
        super(smartwatchBuilder.title, smartwatchBuilder.count, smartwatchBuilder.price, smartwatchBuilder.type);
        this.model = smartwatchBuilder.model;
        this.manufacturer = smartwatchBuilder.manufacturer;
        this.date = smartwatchBuilder.date;
    }

    @Override
    public String toString() {
        return "Smartwatch{" +
                "manufacturer=" + manufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

    public static class SmartwatchBuilder {

        protected final String id;
        protected String title;
        protected Integer count;
        protected Double price;

        private String model;

        private Manufacturer manufacturer;
        protected ProductType type;

        protected LocalDateTime date;


        public SmartwatchBuilder model(String model) {
            this.model = model;
            return this;
        }

        public SmartwatchBuilder(Double price, Manufacturer manufacturer,LocalDateTime date) {
            this.id = UUID.randomUUID().toString();
            this.price = price;
            this.manufacturer = manufacturer;
            type = ProductType.SMARTWATCH;
            this.date = date;
        }
        public SmartwatchBuilder date(LocalDateTime date){
            this.date = date;
            return this;
        }

        public SmartwatchBuilder count(Integer count) {
            this.count = count;
            return this;
        }

        public SmartwatchBuilder title(String title) {
            this.title = title;
            return this;
        }

        private void validateSmartwatch(SmartwatchDAO smartwatch) {
            if (smartwatch.count != null) {
                if (smartwatch.count <= 0) {
                    throw new IllegalArgumentException("Count can't be 0 or less then 0, try again");
                }
            }
            if (smartwatch.title != null) {
                if (smartwatch.title.length() > 20) {
                    throw new IllegalArgumentException("Title can't be larger then 20 symbols, try again");
                }
            }
        }

        public SmartwatchDAO build() {
            SmartwatchDAO smartwatch = new SmartwatchDAO(this);
            validateSmartwatch(smartwatch);
            return smartwatch;
        }
    }
}
