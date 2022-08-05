package com.util;

import com.model.Product;

import java.util.Comparator;
import java.util.Optional;

public class ProductComporator implements Comparator<Product> {


    @Override
    public int compare(Product o1, Product o2) {
        if(o1 == null || o2 == null) {
            throw new IllegalArgumentException("Wrong compare, try again");
        }
        int priceCompare = o1.getPrice().compareTo(o2.getPrice());
        int nameCompare = o1.getTitle().compareTo(o2.getTitle());
        int countCompare = o1.getCount().compareTo(o2.getCount());

        if(priceCompare == 0 ) {
             return nameCompare == 0 ? countCompare : nameCompare;
        }
        else {
            return priceCompare;
        }
    }

}
