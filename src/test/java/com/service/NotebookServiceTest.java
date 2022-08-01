package com.service;

import com.model.Manufacturer;
import com.model.Notebook;
import com.repository.NotebookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotebookServiceTest {

    private final static Notebook notebook = new Notebook("Title", 100, 5555, "Model", Manufacturer.SAMSUNG);
    private NotebookService target;
    private NotebookRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(NotebookRepository.class);
        target = new NotebookService(repository);
    }

    @Test
    void createAndSaveNotebooks_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1));
    }

    @Test
    void createAndSaveNotebooks_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(0));
    }

    @Test
    void createAndSaveNotebooks() {
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
    void createAndSave() {
        int count = 5;
        target.createAndSaveProducts(count);

        ArgumentCaptor<List<Notebook>> argumentCaptor = ArgumentCaptor.forClass((Class) List.class);
        Mockito.verify(repository).saveAll(argumentCaptor.capture());
        Assertions.assertEquals(count, argumentCaptor.getValue().size());
    }

}
