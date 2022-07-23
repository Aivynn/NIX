package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.model.Product;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OptionalExamples<T extends Product> {


    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private final CrudRepository<T> repository;

    private static final Random RANDOM = new Random();


    public OptionalExamples(CrudRepository repository) {
        this.repository = repository;
    }
    public boolean changePrice(String id) {
        return repository.findById(id).map(phone -> {
            LOGGER.info("{}", phone);
            phone.setPrice((double) RANDOM.nextInt(1000));
            repository.update(phone);
            LOGGER.info("{}", phone);
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }

    public boolean delete(String id) {
        return repository.findById(id).map(phone -> {
            LOGGER.info("{}", repository.getAll());
            repository.delete(id);
            LOGGER.info("{}, has been deleted", phone);
            LOGGER.info("{}", repository.getAll());
            return true;
        }).orElseGet(() -> {
            LOGGER.info("No such id, try again");
            return false;
        });
    }
    public void isPhoneCheap(String id) {
        if(repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("No such id, try again");
        }
        List<T> phones = repository.getAll();
        double chosenPhone = repository.findById(id).get().getPrice();
        Product cheapest = phones.get(0);
        double price = 0.0;
        for (Product p : phones) {
            if (p.getPrice() < cheapest.getPrice()) {
                cheapest = p;
            }
            price += p.getPrice();
        }
        price = price / phones.size();
        Product finalCheapest = cheapest;
        double finalPrice = price;
        repository.findById(id)
                .filter(phone -> phone.getPrice() < finalPrice)
                .ifPresentOrElse(
                        phone -> {
                            System.out.println("The average price is " + finalPrice);
                            System.out.println("The chosen phone is cheaper then average price");
                            System.out.println(phone);
                        },
                        () -> {
                            System.out.println("The average price is " + finalPrice);
                            System.out.println("Phone with id " + id + " isn't cheap." + "His price is " + chosenPhone);
                            System.out.println("Here is the cheapest one " + finalCheapest.getId() + "with price " + finalCheapest.getPrice() );
                        }
                );
    }
    public T createPhone(String title) {
        return (T) new Product(title, 0, 0.0) {
        };
    }


    public Optional<T> upsertPhoneTitle(String id, String title) {
        return repository.findById(id).map(phone -> {
            phone.setTitle(title);
            repository.save(phone);
            return phone;
        }).or(() ->
                Optional.of(createPhone(title)));
    }

    public void checkLength(String title) {
        repository.findByTitle(title).map(phone -> phone.getTitle().length() > 5).orElseThrow(() -> new IllegalArgumentException("Title can't be less then 5 chars"));
    }

    public T findOrCreate(String title) {
        if(title.length() < 5) {
            throw new IllegalArgumentException("Title can't be less then 5 chars");
        }
        return repository.findByTitle(title).orElse(createPhone(title));
    }


}
