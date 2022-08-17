package com.repository;

import com.model.Smartwatch;
import com.util.Singleton;

import java.util.*;

@Singleton
public class SmartwatchRepository implements CrudRepository<Smartwatch> {

    private final List<Smartwatch> smartwatchs;

    public SmartwatchRepository() {
        smartwatchs = new LinkedList<>();
    }

    private static SmartwatchRepository instance;


    public static SmartwatchRepository getInstance() {
        if (instance == null) {
            instance = new SmartwatchRepository();
        }
        return instance;
    }

    @Override
    public void save(Smartwatch smartwatch) {
        if (smartwatch != null) {
            smartwatchs.add(smartwatch);
        } else {
            throw new IllegalArgumentException("Smartwatch can't be null");
        }
    }

    @Override
    public boolean update(Smartwatch smartwatch) {
        if (smartwatch == null) {
            throw new IllegalArgumentException("Smartwatch can't be null");
        }
        final Optional<Smartwatch> result = findById(smartwatch.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Smartwatch originSmartwatch = result.get();
        SmartwatchCopy.copy(smartwatch, originSmartwatch);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Smartwatch> iterator = smartwatchs.iterator();
        while (iterator.hasNext()) {
            final Smartwatch smartwatch = iterator.next();
            if (smartwatch.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAll(List<Smartwatch> products) {
        for (Smartwatch smartwatch : products) {
            if (smartwatch != null && findById(smartwatch.getId()).isEmpty()) {
                save(smartwatch);
            } else {
                throw new IllegalArgumentException("Invalid smartwatch to save");
            }
        }

    }

    @Override
    public List<Smartwatch> getAll() {
        if (smartwatchs.isEmpty()) {
            return Collections.emptyList();
        }
        return smartwatchs;
    }

    public Optional<Smartwatch> findById(String id) {
        Smartwatch result = null;
        for (Smartwatch smartwatch : smartwatchs) {
            if (smartwatch.getId().equals(id)) {
                result = smartwatch;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Smartwatch> findByTitle(String title) {
        Smartwatch result = null;
        for (Smartwatch smartwatch : smartwatchs) {
            if (smartwatch.getTitle().equals(title)) {
                result = smartwatch;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class SmartwatchCopy {
        private static void copy(final Smartwatch from, final Smartwatch to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
