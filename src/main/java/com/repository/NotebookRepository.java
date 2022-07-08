package com.repository;

import com.model.Notebook;

import java.util.*;

public class NotebookRepository implements CrudRepository<Notebook> {

    private final List<Notebook> notebooks;

    public NotebookRepository() {
        notebooks = new LinkedList<>();
    }


    @Override
    public void save(Notebook notebook) {
        if(notebook != null) {
            notebooks.add(notebook);
        } else {
            throw new IllegalArgumentException("Notebook can't be null");
        }
    }

    @Override
    public boolean update(Notebook notebook) {
        if(notebook == null) {
            throw new IllegalArgumentException("Notebook can't be null");
        }
        final Optional<Notebook> result = findById(notebook.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Notebook originNotebook = result.get();
        NotebookCopy.copy(notebook,originNotebook);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Notebook> iterator = notebooks.iterator();
        while (iterator.hasNext()) {
            final Notebook notebook = iterator.next();
            if (notebook.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAll(List<Notebook> products) {
        for (Notebook notebook : products) {
            if(notebook != null && findById(notebook.getId()).isEmpty()) {
                save(notebook);
            }
            else {
                throw new IllegalArgumentException("Invalid notebook to save");
            }
        }

    }

    @Override
    public List<Notebook> getAll() {
        if (notebooks.isEmpty()) {
            return Collections.emptyList();
        }
        return notebooks;
    }

    public Optional<Notebook> findById(String id) {
        Notebook result = null;
        for (Notebook notebook : notebooks) {
            if (notebook.getId().equals(id)) {
                result = notebook;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class NotebookCopy {
        private static void copy(final Notebook from, final Notebook to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
