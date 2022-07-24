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
    void saveNotebook() {
        target.saveNotebook(notebook);

        ArgumentCaptor<Notebook> argument = ArgumentCaptor.forClass(Notebook.class);
        Mockito.verify(repository).update(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void saveNotebook_zeroCount() {
        notebook.setCount(0);
        target.saveNotebook(notebook);

        ArgumentCaptor<Notebook> argument = ArgumentCaptor.forClass(Notebook.class);
        Mockito.verify(repository).update(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }

    @Test
    void changePrice() {
        double previousPrice = notebook.getPrice();
        Mockito.when(repository.findById(notebook.getId())).thenReturn(Optional.of(notebook));
        target.changePrice(notebook.getId());
        Mockito.verify(repository).update(notebook);
        Assertions.assertNotEquals(previousPrice, notebook.getPrice());
    }

    @Test
    void delete() {
        Mockito.when(repository.findById(notebook.getId())).thenReturn(Optional.of(notebook));

        boolean delete = target.delete(notebook.getId());

        Mockito.verify(repository).findById(notebook.getId());
        Mockito.verify(repository).delete(notebook.getId());
        Assertions.assertTrue(delete);
    }

    @Test
    void deleteWhenIdIsNotPresent() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        boolean delete = target.delete(notebook.getId());

        Mockito.verify(repository).findById(notebook.getId());
        Mockito.verify(repository, times(0)).delete(notebook.getId());
        Assertions.assertFalse(delete);
    }

    @Test
    void createAndSave() {
        int count = 5;
        target.createAndSaveProducts(count);

        ArgumentCaptor<List<Notebook>> argumentCaptor = ArgumentCaptor.forClass((Class) List.class);
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
        Mockito.when(repository.findById(notebook.getId())).thenReturn(Optional.of(notebook));
        boolean delete = target.delete(notebook.getId());
        verify(repository).delete(isA(String.class));
        Assertions.assertTrue(delete);
    }


}
