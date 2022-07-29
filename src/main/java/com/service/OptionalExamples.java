package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalExamples{


    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private final PhoneRepository repository;

    private static final Random RANDOM = new Random();


    public OptionalExamples(PhoneRepository repository) {
        this.repository = repository;
    }


    public Phone createPhone(String title) {
       return new Phone(title, 100, 956.74, "Model", Manufacturer.APPLE, Stream.of("foo", "bar")
               .collect(Collectors.toList()));
    }

    public void checkLength(String title) {
        repository.findByTitle(title).filter(phone -> phone.getTitle().length() > 5).orElseThrow(() -> new IllegalArgumentException("Title can't be less then 5 chars"));
    }

    public Phone findOrCreate(String title) {
        if(title.length() < 5) {
            throw new IllegalArgumentException("Title can't be less then 5 chars");
        }
        return repository.findByTitle(title).orElse(createPhone(title));
    }

    public Optional<Phone> updateOrCreate(String id, String title) {
        return repository.findById(id).map(phone -> {
            phone.setTitle(title);
            return phone;
        }).or(() ->
                java.util.Optional.of(createPhone(title)));
    }

    public Phone findByTitleOrCreate(String title) {
      return repository.findByTitle(title)
              .orElseGet(() -> createPhone(title));
    }

    public void changePrice(String id,double price) {
        repository.findById(id).ifPresent(phone -> phone.setPrice(price));
    }

    public void upsertPhone(Phone phone) {
        repository.findById(phone.getId())
                .ifPresentOrElse(repository::update, () -> createPhone(phone.getTitle()));

    }

}
