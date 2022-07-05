package com.repository;

import com.model.Smartwatch;

import java.util.*;

public class SmartwatchRepository implements CrudRepository<Smartwatch> {

    private final List<Smartwatch> smartwatches;

    public SmartwatchRepository() {
        smartwatches = new LinkedList<>();
    }


    @Override
    public void save(Smartwatch smartwatch) {
        smartwatches.add(smartwatch);
    }

    @Override
    public boolean update(Smartwatch smartwatch) {
        final Optional<Smartwatch> result = findById(smartwatch.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Smartwatch originSmartwatch = result.get();
        SmartwatchRepository.SmartwatchCopy.copy(smartwatch, originSmartwatch);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Smartwatch> iterator = smartwatches.iterator();
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
    public void saveAll(List<Smartwatch> smartwatches) {
        for (Smartwatch smartwatch : smartwatches) {
            save(smartwatch);
        }

    }

    public List<Smartwatch> getAll() {
        if (smartwatches.isEmpty()) {
            return Collections.emptyList();
        }
        return smartwatches;
    }

    public Optional<Smartwatch> findById(String id) {
        Smartwatch result = null;
        for (Smartwatch notebook : smartwatches) {
            if (notebook.getId().equals(id)) {
                result = notebook;
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
