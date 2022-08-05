package com.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Smartwatch extends Product {

    private final String model;
    private final Manufacturer manufacturer;

    private Smartwatch(SmartwatchBuilder smartwatchBuilder) {
        super(smartwatchBuilder.title,smartwatchBuilder.count,smartwatchBuilder.price,smartwatchBuilder.type);
        this.model = smartwatchBuilder.model;
        this.manufacturer = smartwatchBuilder.manufacturer;
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

        public SmartwatchBuilder model(String model) {
            this.model = model;
            return this;
        }
        public SmartwatchBuilder(Double price, Manufacturer manufacturer) {
            this.id = UUID.randomUUID().toString();
            this.price = price;
            this.manufacturer = manufacturer;
        }

        public SmartwatchBuilder count(Integer count) {
            this.count = count;
            return this;
        }

        public SmartwatchBuilder title(String title) {
            this.title = title;
            return this;
        }

        private void validateSmartwatch(Smartwatch smartwatch) {
            if(smartwatch.count != null) {
                if(smartwatch.count <= 0) {
                    throw new IllegalArgumentException("Count can't be 0 or less then 0, try again");
                }
            }
            if(smartwatch.title != null) {
                if(smartwatch.title.length() > 20) {
                    throw new IllegalArgumentException("Title can't be larger then 20 symbols, try again");
                }
            }
        }

        public Smartwatch build() {
            Smartwatch smartwatch = new Smartwatch(this);
            validateSmartwatch(smartwatch);
            return smartwatch;
        }
    }
}
