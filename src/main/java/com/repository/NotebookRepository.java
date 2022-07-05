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
        notebooks.add(notebook);
    }

    @Override
    public boolean update(Notebook notebook) {
        final Optional<Notebook> result = findById(notebook.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Notebook originNotebook = result.get();
        NotebookRepository.NotebookCopy.copy(notebook, originNotebook);
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
    public void saveAll(List<Notebook> notebooks) {
        for (Notebook notebook : notebooks) {
            save(notebook);
        }

    }

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