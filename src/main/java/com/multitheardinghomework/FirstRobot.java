package com.multitheardinghomework;

import lombok.SneakyThrows;

import java.util.Random;


public class FirstRobot implements Runnable {

    private static final RoboFactory roboFactory = RoboFactory.getInstanceUsingDoubleLocking();

    private Integer value = 0;

    private final FuelContainer buffer;

    FirstRobot(FuelContainer buffer) {
        this.buffer = buffer;
    }


    @SneakyThrows
    @Override
    public void run() {
        Random random = roboFactory.getRandom();
        while (roboFactory.getFlag()) {
            value = random.nextInt(500, 1000);
            System.out.println("Robot1 delivered " + value + " fuel ");
            buffer.setValue(value);
            Thread.sleep(3000);
        }
        System.out.println("Robot 1 have finished his work");
    }
}
