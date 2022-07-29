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

import java.util.List;
import java.util.Optional;;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PhoneServiceTest {
    final Phone phone = new Phone("Title", 100, 1000.0, "Model", Manufacturer.APPLE);
    private PhoneService target;
    private PhoneRepository repository;


    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PhoneRepository.class);
        target = new PhoneService(repository);
    }

    @Test
    void createAndSavePhones_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1));
    }

    @Test
    void createAndSavePhones_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(0));
    }

    @Test
    void createAndSavePhones() {
        target.createAndSaveProducts(2);
        Mockito.verify(repository).saveAll(Mockito.anyList());
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void printAll() {
        target.printAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void updatePhone() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", Manufacturer.APPLE);
        target.savePhone(phone);

        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).update(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void savePhone_zeroCount() {
        phone.setCount(0);
        target.savePhone(phone);

        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).update(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }

    @Test
    void changePrice() {
        double previousPrice = phone.getPrice();
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.changePrice(phone.getId());
        Mockito.verify(repository).update(phone);
        Assertions.assertNotEquals(previousPrice, phone.getPrice());
    }

    @Test
    void delete() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));

        Mockito.verify(repository).findById(phone.getId());
        Mockito.verify(repository).delete(phone.getId());
    }


    @Test
    void createAndSave() {
        int count = 5;
        target.createAndSaveProducts(count);

        ArgumentCaptor<List<Phone>> argumentCaptor = ArgumentCaptor.forClass((Class) List.class);
        Mockito.verify(repository).saveAll(argumentCaptor.capture());
        Assertions.assertEquals(count, argumentCaptor.getValue().size());
    }

    @Test
    void createAndSaveNotCalled() {
        int count = -5;

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(count));
    }

    @Test
    void isIdValid() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));;
        verify(repository).delete(isA(String.class));
    }

    @Test
    public void updateTitle() {
        String title = "asdfg";
        Mockito.when(repository.update(argThat(phone -> {

            Assertions.assertEquals(title, phone.getTitle());
            return true;
        }))).thenReturn(true);
        boolean saved = target.updateTitle(phone, title);
        Assertions.assertTrue(saved);
    }

    @Test
    public void updateWhenTitleNotValid() {
        String title = "as";
        boolean saved = target.updateTitle(phone, title);
        Assertions.assertFalse(saved);
        Mockito.verify(repository, times(0)).update(any(Phone.class));
    }

    @Test
    public void updateTitleWithRealMethod() {
        Mockito.when((repository).update(phone)).thenCallRealMethod();

        boolean result = target.updateTitle(phone, "asdfg");
        Assertions.assertFalse(result);
    }
}
