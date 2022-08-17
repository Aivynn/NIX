package examples.model;

import lombok.Data;

@Data
public class NotifiableProduct extends Product implements Notification {
    protected String channel;

    public NotifiableProduct(long id, boolean available,String title, double price, String channel) {
        super(id,available,title,price);
        this.channel = channel;
    }
    public NotifiableProduct() {

    }

    @Override
    public String generateAddressForNotification() {
        return "somerandommail@gmail.com";
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}