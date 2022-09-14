package com;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumbers {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0;i<100;i++) {
            numbers.add(i);
        }
        List<Integer> primeNumbers = primeNumbersManufacturer(numbers);
        Thread.sleep(100);
        System.out.println(primeNumbers);
    }


    public static List<Integer> primeNumbersManufacturer(List<Integer> numbers) throws InterruptedException {
        List<Integer> primeNumbers = new ArrayList<>();
        Thread thread1 = new Thread(() -> numbers.stream()
                .limit(numbers.size()/2)
                .forEach(x -> {
                    if(isNumberSimple(x)){
                        primeNumbers.add(x);
                    }

        }));
        Thread thread2 = new Thread(() -> numbers.stream()
                .skip(numbers.size() / 2)
                .forEach(x -> {
                    if(isNumberSimple(x)){
                        primeNumbers.add(x);
                    }

                }));
        thread1.start();
        thread2.start();
        return primeNumbers;
    }


    private static boolean isNumberSimple(Integer i) {
        int c = 1;
        List<Integer> divisors = new ArrayList<>();
        divisors.add(i);
        while (c <= i / 2) {
            if (i % c == 0) {
                divisors.add(c);
            }
            c++;
        }
        return divisors.size() <= 2;
    }
}

