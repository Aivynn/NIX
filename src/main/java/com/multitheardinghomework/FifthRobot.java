package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class FifthRobot implements Callable<Boolean> {

    private static final RoboFactory roboFactory = RoboFactory.getInstanceUsingDoubleLocking();

    private final FuelContainer buffer;

    private final CountDownLatch s;

    FifthRobot(CountDownLatch s, FuelContainer buffer) {
        this.s = s;
        this.buffer = buffer;
    }

    @Override
    public Boolean call() throws Exception {
        s.await();
        Random random = roboFactory.getRandom();
        int job = 0;
        while (roboFactory.getFlag()) {
            int value = buffer.getValue().get();
            int requiredFuel = random.nextInt(350, 700);
            buffer.setValue(-requiredFuel);
            if (value < requiredFuel) {
                System.out.println("Not enough fuel, waiting for another supply  ");
                continue;
            }
            job += 10;
            if (job == 100) {
                roboFactory.setFlag(false);
            }
        }
        System.out.println("Robot 5 have finished his work");
        return true;
    }
}

