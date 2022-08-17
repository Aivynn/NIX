package com.util;

import com.model.Phone;
import com.model.Product;
import com.repository.CrudRepository;
import com.repository.PhoneRepository;
import com.service.PhoneService;
import com.service.ProductService;
import com.test;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Annotations {

    Map<String, CrudRepository<? extends Product>> repositories = new HashMap<>();
    Map<String, ProductService<? extends Product>> services = new HashMap<>();


    public <T extends CrudRepository> void repositories() throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        final Reflections reflections = new Reflections("com");
        Set<Class<? extends CrudRepository>> allClasses = reflections.getSubTypesOf(CrudRepository.class);
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Singleton.class)) {
                Constructor<?> constructor = clazz.getConstructor();
                CrudRepository<? extends Product> repository = (T) constructor.newInstance();
                repositories.put(clazz.getSimpleName(), repository);
            }
        }
        System.out.println(repositories);
    }

    public <T extends ProductService> void services() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Reflections reflections = new Reflections("com");
        List<String> keys = repositories.keySet().stream().toList();
        Set<Class<? extends ProductService>> allClasses = reflections.getSubTypesOf(ProductService.class);
        for (Class<? extends ProductService> clazz : allClasses) {
            for (String key : keys) {
                if (key.substring(0, 5).equals(clazz.getSimpleName().substring(0, 5))) {
                    Constructor<?> constructor = clazz.getConstructor(repositories.get(key).getClass());
                    ProductService<? extends Product> instance = (T) constructor.newInstance(repositories.get(key));
                    services.put(clazz.getSimpleName(), instance);
                }
            }
        }

    }

    public void autowired() throws NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        final Reflections reflections = new Reflections("com");
        Set<Class<? extends ProductService>> allClasses = reflections.getSubTypesOf(ProductService.class);
        for (Class<?> clazz : allClasses) {
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    ProductService<? extends Product> load = services.get(clazz.getSimpleName());
                    Field instance = clazz.getDeclaredField("instance");
                    instance.setAccessible(true);
                    Object value = instance.get(clazz.getSimpleName());
                    if (value == null) {
                        value = load;
                    }
                    instance.set(clazz.getSimpleName(), value);
                    Class.forName(clazz.getName());

                }
            }
        }
    }

    public <T extends ProductService> void autowiredFields() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        final Reflections reflections = new Reflections("com",new FieldAnnotationsScanner());
        Set<Field> allClasses = reflections.getFieldsAnnotatedWith(Autowired.class);
        for(Field field : allClasses) {
        field.setAccessible(true);
        ProductService<? extends Product> service = (T) field.get(field.getType());
        if (service == null) {
            System.out.println(field.getType());
            ProductService<? extends Product> load = services.get(field.getType().getSimpleName());
            service = (T) load;
        }
        field.set(field.getDeclaringClass(), service);
        }
    }
}















































