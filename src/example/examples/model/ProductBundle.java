package examples.model;

import lombok.Setter;

@Setter
public class ProductBundle extends Product implements Amount {
    protected int amount;

    public ProductBundle(long id, boolean available, String title, double price, int amount) {
        super(id, available, title, price);
        this.amount = amount;
    }

    public ProductBundle() {

    }

    @Override
    public int getAmountInBundle() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                "amount=" + amount +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}