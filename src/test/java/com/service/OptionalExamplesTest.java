package com.service;

import com.model.Manufacturer;
import com.model.Phone;
import com.model.Product;
import com.repository.PhoneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class OptionalExamplesTest {

    final Phone phone = new Phone("Title", 100, 1000.0, "Model", Manufacturer.APPLE);
    private PhoneRepository repository;

    private OptionalExamples target;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PhoneRepository.class);
        target = new OptionalExamples(repository);
    }


    @Test
    void checkLength() {
        target.checkLength(phone.getTitle());
        Mockito.verify(repository.findByTitle(phone.getTitle()));
    }
    @Test
    void checkLengthFail() {
        String title = "asd";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.checkLength(title));

    }

    @Test
    public void findOrCreate() {
        String title = "asdfgh";
        Mockito.when(repository.findByTitle(title)).thenReturn(Optional.of(phone));
        Product phone1 = target.findOrCreate(title);

        Assertions.assertEquals(phone1, phone);
    }

    @Test
    public void findOrCreateEmpty() {
        String title = "asdfgh";
        Mockito.when(repository.findByTitle(title)).thenReturn(Optional.empty());
        String createdPhoneTitle = target.findOrCreate(title).getTitle();

        Assertions.assertEquals(title,createdPhoneTitle);
    }
    @Test
    public void upsertPhoneTitle() {
        String title = "asdfgh";
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.updateOrCreate(phone.getId(),title).get();
        ArgumentCaptor<Phone> argumentCaptor = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).update(argumentCaptor.capture());
        Assertions.assertEquals(title,argumentCaptor.getValue().getTitle());
    }

    @Test
    public void upsert() {
        String title = "asdfgh";
        Mockito.when(repository.findById("qqwww")).thenReturn(Optional.empty());
        Phone result = target.updateOrCreate("qqwww",title).get();
        Assertions.assertEquals(title, result.getTitle());
    }

    @Test
    public void findByTitle() {
        Mockito.when(repository.findByTitle(phone.getTitle())).thenReturn(Optional.of(phone));
        Phone test = target.findByTitleOrCreate(phone.getTitle());

        Assertions.assertEquals(phone.getTitle(),test.getTitle());
    }

    @Test
    public void findByTitleCreate() {
        String title = "aafffggg";
        Mockito.when(repository.findByTitle(title)).thenReturn(Optional.empty());
        Phone test = target.findByTitleOrCreate(title);

        Assertions.assertEquals(title,test.getTitle());
    }

    @Test
    public void upsertPhone() {
        Phone test = new Phone("a234", 100, 600, "Model", Manufacturer.APPLE);
        Mockito.when(repository.findById(test.getId())).thenReturn(Optional.of(test));
        target.upsertPhone(test);
        Mockito.verify(repository).update(test);
    }

    @Test
    public void upsertPhoneNotFound() {
        Phone test = new Phone("a234", 100, 600, "Model", Manufacturer.APPLE);
        Mockito.when(repository.findById(test.getId())).thenReturn(Optional.empty());
        target.upsertPhone(test);
        Mockito.verify(repository,times(0)).update(any(Phone.class));
    }
}
