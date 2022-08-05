package examples.model;

import lombok.Data;

@Data
public abstract class Product {
    protected long id;
    protected boolean available;
    protected String title;
    protected double price;

    public Product(long id, boolean available, String title, double price) {
        this.id = id;
        this.available = available;
        this.title = title;
        this.price = price;
    }

    public Product() {
    }
}