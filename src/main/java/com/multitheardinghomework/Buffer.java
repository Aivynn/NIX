package com.multitheardinghomework;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Buffer {
    private AtomicInteger value = new AtomicInteger(0);

    public void setValue(Integer number){
        value.set(value.get() + number);
        System.out.println("Value have been changed to " + value.get());
    }
}
