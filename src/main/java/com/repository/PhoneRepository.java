package com.repository;

import com.model.Phone;
import com.util.Singleton;

import java.util.*;

@Singleton
public class PhoneRepository implements CrudRepository<Phone> {

    private final List<Phone> phones;

    public PhoneRepository() {
        phones = new LinkedList<>();
    }

    private static PhoneRepository instance;


    @Override
    public void save(Phone phone) {
        if(phone != null) {
            phones.add(phone);
        } else {
            throw new IllegalArgumentException("Phone can't be null");
        }
    }

    public static PhoneRepository getInstance() {
        if (instance == null) {
            instance = new PhoneRepository();
        }
        return instance;
    }

    @Override
    public boolean update(Phone phone) {
        final Optional<Phone> result = findById(phone.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Phone originPhone = result.get();
        PhoneCopy.copy(phone,originPhone);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Phone> iterator = phones.iterator();
        while (iterator.hasNext()) {
            final Phone phone = iterator.next();
            if (phone.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAll(List<Phone> products) {
        for (Phone phone : products) {
            if(phone != null && findById(phone.getId()).isEmpty()) {
                save(phone);
            }
            else {
                throw new IllegalArgumentException("Invalid phone to save");
            }
        }

    }

    @Override
    public List<Phone> getAll() {
        if (phones.isEmpty()) {
            return Collections.emptyList();
        }
        return phones;
    }

    public Optional<Phone> findById(String id) {
        Phone result = null;
        for (Phone phone : phones) {
            if (phone.getId().equals(id)) {
                result = phone;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Phone> findByTitle(String title) {
        Phone result = null;
        for (Phone phone : phones) {
            if (phone.getTitle().equals(title)) {
                result = phone;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class PhoneCopy {
        private static void copy(final Phone from, final Phone to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
