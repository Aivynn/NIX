package com.util;

import com.model.Product;
import com.service.ProductService;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Annotations {

    private final Map<String, Object> products = new HashMap<>();


    public void repositories() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        final Reflections reflections = new Reflections("com");
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Singleton.class);
        for (Class<?> clazz : allClasses) {
            products.put(clazz.getSimpleName(), createBean(clazz));
        }}

    private Object createBean(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor;
        if (clazz.getConstructors().length > 0) {
            constructor = clazz.getConstructors()[0];
        } else {
            constructor = clazz.getConstructor();
        }
        if (constructor.isAnnotationPresent(Autowired.class)) {
            Class<?>[] types = constructor.getParameterTypes();
            Object[] instances = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                if (products.containsKey(types[i].getSimpleName())) {
                    instances[i] = products.get(types[i].getSimpleName());
                } else {
                    Object bean = createBean(types[i]);
                    instances[i] = bean;
                    products.put(types[i].getSimpleName(),bean);
                }
            }
            return constructor.newInstance(instances);
        }
        return constructor.newInstance();
    }



    public void autowiredFields() throws IllegalAccessException {
        final Reflections reflections = new Reflections("com",new FieldAnnotationsScanner());
        Set<Field> allClasses = reflections.getFieldsAnnotatedWith(Autowired.class);
        for(Field field : allClasses) {
            field.setAccessible(true);
            Object service = field.get(field.getType());
            if (service == null) {
                service = products.get(field.getType().getSimpleName());
            }
            field.set(field.getDeclaringClass(), service);
        }
    }
}