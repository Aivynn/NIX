package com.service;

import com.model.Manufacturer;
import com.model.Smartwatch;
import com.repository.SmartwatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SmartwatchServiceTest {

    private final static Smartwatch smartwatch = new Smartwatch("Title", 100, 5555, "Model", Manufacturer.SAMSUNG);
    private SmartwatchService target;
    private SmartwatchRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SmartwatchRepository.class);
        target = new SmartwatchService(repository);
    }

    @Test
    void createAndSaveSmartwatchs_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveSmartwatches(-1));
    }

    @Test
    void createAndSaveSmartwatchs_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveSmartwatches(0));
    }

    @Test
    void createAndSaveSmartwatchs() {
        target.createAndSaveSmartwatches(2);
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
    void saveSmartwatch() {
        target.createAndSaveSmartwatches(1);

        ArgumentCaptor<List<Smartwatch>> argument = ArgumentCaptor.forClass(List.class);
        Mockito.verify(repository).saveAll(argument.capture());
        Assertions.assertTrue(argument.getValue().get(0).getTitle().contains("Title"), "Title");
    }

    @Test
    void saveSmartwatch_zeroCount() {
        smartwatch.setCount(0);
        target.saveSmartwatch(smartwatch);

        ArgumentCaptor<Smartwatch> argument = ArgumentCaptor.forClass(Smartwatch.class);
        Mockito.verify(repository).update(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }

    @Test
    void changePrice() {
        double previousPrice = smartwatch.getPrice();
        Mockito.when(repository.findById(smartwatch.getId())).thenReturn(Optional.of(smartwatch));
        target.changePrice(smartwatch.getId());
        Mockito.verify(repository).update(smartwatch);
        Assertions.assertNotEquals(previousPrice, smartwatch.getPrice());
    }

    @Test
    void delete() {
        Mockito.when(repository.findById(smartwatch.getId())).thenReturn(Optional.of(smartwatch));

        boolean delete = target.delete(smartwatch.getId());

        Mockito.verify(repository).findById(smartwatch.getId());
        Mockito.verify(repository).delete(smartwatch.getId());
        Assertions.assertTrue(delete);
    }

    @Test
    void deleteWhenIdIsNotPresent() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        boolean delete = target.delete(smartwatch.getId());

        Mockito.verify(repository).findById(smartwatch.getId());
        Mockito.verify(repository, times(0)).delete(smartwatch.getId());
        Assertions.assertFalse(delete);
    }

    @Test
    void createAndSave() {
        int count = 5;
        target.createAndSaveSmartwatches(count);

        ArgumentCaptor<List<Smartwatch>> argumentCaptor = ArgumentCaptor.forClass((Class) List.class);
        Mockito.verify(repository).saveAll(argumentCaptor.capture());
        Assertions.assertEquals(count, argumentCaptor.getValue().size());
    }

    @Test
    void createAndSaveNotCalled() {
        int count = -5;

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveSmartwatches(count));
    }

    @Test
    void isIdValid() {
        Mockito.when(repository.findById(smartwatch.getId())).thenReturn(Optional.of(smartwatch));
        boolean delete = target.delete(smartwatch.getId());
        verify(repository).delete(isA(String.class));
        Assertions.assertTrue(delete);
    }


}
