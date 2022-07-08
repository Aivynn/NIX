package com.repository;

import com.model.Manufacturer;
import com.model.Notebook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class NotebookRepositoryTest {

    private NotebookRepository target;

    private Notebook notebook;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new NotebookRepository();
        notebook = new Notebook(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextInt(1000),
                "Model-" + random.nextInt(10),
                Manufacturer.APPLE
        );
    }

    @Test
    void save() {
        target.save(notebook);
        final List<Notebook> notebooks = target.getAll();
        Assertions.assertEquals(1, notebooks.size());
        Assertions.assertEquals(notebooks.get(0).getId(), notebook.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singleNotebook() {
        target.saveAll(Collections.singletonList(notebook));
        final List<Notebook> notebooks = target.getAll();
        Assertions.assertEquals(1, notebooks.size());
        Assertions.assertEquals(notebooks.get(0).getId(), notebook.getId());
    }

    @Test
    void saveAll_noNotebook() {
        target.saveAll(Collections.emptyList());
        final List<Notebook> notebooks = target.getAll();
        Assertions.assertEquals(0, notebooks.size());
    }

    @Test
    void saveAll_manyNotebooks() {
        final Notebook otherNotebook = new Notebook("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        target.saveAll(List.of(notebook, otherNotebook));
        final List<Notebook> notebooks = target.getAll();
        Assertions.assertEquals(2, notebooks.size());
        Assertions.assertEquals(notebooks.get(0).getId(), notebook.getId());
        Assertions.assertEquals(notebooks.get(1).getId(), otherNotebook.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Notebook> notebooks = new ArrayList<>();
        notebooks.add(notebook);
        notebooks.add(notebook);
        Assertions.assertThrows(IllegalArgumentException.class, () ->target.saveAll(notebooks));
    }

    @Test
    void saveAll_hasNull() {
        final List<Notebook> notebooks = new ArrayList<>();
        notebooks.add(notebook);
        notebooks.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(notebooks));
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(notebook);
        notebook.setTitle(newTitle);

        final boolean result = target.update(notebook);

        Assertions.assertTrue(result);
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(newTitle, actualResult.get(0).getTitle());
        Assertions.assertEquals(notebook.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(notebook.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noNotebook() {
        target.save(notebook);
        final Notebook noNotebook = new Notebook("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        final boolean result = target.update(noNotebook);

        Assertions.assertFalse(result);
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(notebook.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(notebook.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(notebook);
        final boolean result = target.delete(notebook.getId());
        Assertions.assertTrue(result);
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noNotebook() {
        target.save(notebook);
        final Notebook noNotebook = new Notebook("Title", 500, 1000.0, "Model", Manufacturer.APPLE);
        final boolean result = target.delete(noNotebook.getId());
        Assertions.assertFalse(result);
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(notebook);
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noNotebooks() {
        final List<Notebook> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(notebook);
        final Optional<Notebook> optionalNotebook = target.findById(notebook.getId());
        Assertions.assertTrue(optionalNotebook.isPresent());
        final Notebook actualNotebook = optionalNotebook.get();
        Assertions.assertEquals(notebook.getId(),actualNotebook.getId());
    }
    @Test
    void updateNull() {
        Assertions.assertThrows(IllegalArgumentException.class,() -> target.update(null));
    }
}