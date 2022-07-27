package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;

public class OptionalExamples{


    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private final PhoneRepository repository;

    private static final Random RANDOM = new Random();


    public OptionalExamples(PhoneRepository repository) {
        this.repository = repository;
    }

    public void isPhoneCheap(String id) {
        if(repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("No such id, try again");
        }
        double chosenPhone = repository.findById(id).get().getPrice();
        Phone cheapest = repository.findCheapest().orElseThrow(IllegalArgumentException::new);
        double price = repository.averagePrice() / repository.size();
        repository.findById(id)
                .filter(phone -> phone.getPrice() < price)
                .ifPresentOrElse(
                        phone -> {
                            System.out.println("The average price is " + price);
                            System.out.println("The chosen phone is cheaper then average price");
                            System.out.println(phone);
                        },
                        () -> {
                            System.out.println("The average price is " + price);
                            System.out.println("Phone with id " + id + " isn't cheap." + "His price is " + chosenPhone);
                            System.out.println("Here is the cheapest one " + cheapest.getId() + "with price " + cheapest.getPrice() );
                        }
                );
    }
    public Phone createPhone(String title) {
       return new Phone(title, 100, 956.74, "Model", Manufacturer.APPLE);
    }

    public void checkLength(String title) {
        repository.findByTitle(title).map(phone -> phone.getTitle().length() > 5).orElseThrow(() -> new IllegalArgumentException("Title can't be less then 5 chars"));
    }

    public Phone findOrCreate(String title) {
        if(title.length() < 5) {
            throw new IllegalArgumentException("Title can't be less then 5 chars");
        }
        return repository.findByTitle(title).orElse(createPhone(title));
    }

    public Optional<Phone> upsertPhoneTitle(String id, String title) {
        return repository.findById(id).map(phone -> {
            phone.setTitle(title);
            repository.update(phone);
            return phone;
        }).or(() ->
                java.util.Optional.of(createPhone(title)));
    }

    public Phone findByTitleOrCreate(String title) {
      return repository.findByTitle(title)
              .orElseGet(() -> createPhone(title));
    }

    public void changePrice(String id,double price) {
        repository.findById(id)
                .ifPresent(phone -> phone.setPrice(price));
    }

}
