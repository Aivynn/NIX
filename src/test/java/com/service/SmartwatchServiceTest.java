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

    private final static Smartwatch smartwatch = new Smartwatch.SmartwatchBuilder(1000.0)
            .count(500)
            .model( "Model")
            .title("title")
            .manufacturer(Manufacturer.APPLE)
            .build();
    private SmartwatchService target;
    private SmartwatchRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SmartwatchRepository.class);
        target = new SmartwatchService(repository);
    }

    @Test
    void createAndSaveSmartwatchs_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1));
    }

    @Test
    void createAndSaveSmartwatchs_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(0));
    }

    @Test
    void createAndSaveSmartwatchs() {
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
    void changePrice() {
        double previousPrice = smartwatch.getPrice();
        Mockito.when(repository.findById(smartwatch.getId())).thenReturn(Optional.of(smartwatch));
        target.changePrice(smartwatch.getId());
        Mockito.verify(repository).update(smartwatch);
        Assertions.assertNotEquals(previousPrice, smartwatch.getPrice());
    }
    @Test
    void createAndSave() {
        int count = 5;
        target.createAndSaveProducts(count);

        ArgumentCaptor<List<Smartwatch>> argumentCaptor = ArgumentCaptor.forClass((Class) List.class);
        Mockito.verify(repository).saveAll(argumentCaptor.capture());
        Assertions.assertEquals(count, argumentCaptor.getValue().size());
    }
}