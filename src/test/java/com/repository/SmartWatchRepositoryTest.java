package com.repository;

import com.model.Manufacturer;
import com.model.Smartwatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class SmartwatchRepositoryTest {

    private SmartwatchRepository target;

    private Smartwatch smartwatch;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new SmartwatchRepository();
        smartwatch = new Smartwatch.SmartwatchBuilder((double) random.nextInt(1000),Manufacturer.APPLE)
                .title("Title-" + random.nextInt(1000))
                .count(random.nextInt(500))
                .model("Model-" + random.nextInt(10))
                .build();
    }

    @Test
    void save() {
        target.save(smartwatch);
        final List<Smartwatch> smartwatchs = target.getAll();
        Assertions.assertEquals(1, smartwatchs.size());
        Assertions.assertEquals(smartwatchs.get(0).getId(), smartwatch.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singleSmartwatch() {
        target.saveAll(Collections.singletonList(smartwatch));
        final List<Smartwatch> smartwatchs = target.getAll();
        Assertions.assertEquals(1, smartwatchs.size());
        Assertions.assertEquals(smartwatchs.get(0).getId(), smartwatch.getId());
    }

    @Test
    void saveAll_noSmartwatch() {
        target.saveAll(Collections.emptyList());
        final List<Smartwatch> smartwatchs = target.getAll();
        Assertions.assertEquals(0, smartwatchs.size());
    }

    @Test
    void saveAll_manySmartwatchs() {
        final Smartwatch otherSmartwatch = new Smartwatch.SmartwatchBuilder(1000.0,Manufacturer.APPLE)
                .count(500)
                .model( "Model")
                .title("title")
                .build();
        target.saveAll(List.of(smartwatch, otherSmartwatch));
        final List<Smartwatch> smartwatchs = target.getAll();
        Assertions.assertEquals(2, smartwatchs.size());
        Assertions.assertEquals(smartwatchs.get(0).getId(), smartwatch.getId());
        Assertions.assertEquals(smartwatchs.get(1).getId(), otherSmartwatch.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Smartwatch> smartwatchs = new ArrayList<>();
        smartwatchs.add(smartwatch);
        smartwatchs.add(smartwatch);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(smartwatchs));
    }

    @Test
    void saveAll_hasNull() {
        final List<Smartwatch> smartwatchs = new ArrayList<>();
        smartwatchs.add(smartwatch);
        smartwatchs.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(smartwatchs));
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(smartwatch);
        smartwatch.setTitle(newTitle);

        final boolean result = target.update(smartwatch);

        Assertions.assertTrue(result);
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(newTitle, actualResult.get(0).getTitle());
        Assertions.assertEquals(smartwatch.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(smartwatch.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noSmartwatch() {
        target.save(smartwatch);
        final Smartwatch noSmartwatch = new Smartwatch.SmartwatchBuilder(1000.0,Manufacturer.APPLE)
                .count(500)
                .model( "Model")
                .title("title")
                .build();
        final boolean result = target.update(noSmartwatch);

        Assertions.assertFalse(result);
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(smartwatch.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(smartwatch.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(smartwatch);
        final boolean result = target.delete(smartwatch.getId());
        Assertions.assertTrue(result);
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noSmartwatch() {
        target.save(smartwatch);
        final Smartwatch noSmartwatch = new Smartwatch.SmartwatchBuilder(1000.0,Manufacturer.APPLE)
                .count(500)
                .model( "Model")
                .title("title")
                .build();
        final boolean result = target.delete(noSmartwatch.getId());
        Assertions.assertFalse(result);
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(smartwatch);
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noSmartwatchs() {
        final List<Smartwatch> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(smartwatch);
        final Optional<Smartwatch> optionalSmartwatch = target.findById(smartwatch.getId());
        Assertions.assertTrue(optionalSmartwatch.isPresent());
        final Smartwatch actualSmartwatch = optionalSmartwatch.get();
        Assertions.assertEquals(smartwatch.getId(), actualSmartwatch.getId());
    }

    @Test
    void updateNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }
}