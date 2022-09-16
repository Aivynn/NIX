package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class FifthRobot implements Callable<Boolean> {

    private static final MultiThreadingHomework multiThreadingHomework = MultiThreadingHomework.getInstanceUsingDoubleLocking();

    private final Buffer buffer;

    private final CountDownLatch s;

    FifthRobot(CountDownLatch s, Buffer buffer) {
        this.s = s;
        this.buffer = buffer;
    }

    @Override
    public Boolean call() throws Exception {
        s.await();
        Random random = multiThreadingHomework.getRandom();
        int job = 0;
        while (multiThreadingHomework.getFlag()) {
            Thread.sleep(1000);
            int value = buffer.getValue().get();
            int requiredGas = random.nextInt(350, 700);
            buffer.setValue(-requiredGas);
            if (value < requiredGas) {
                System.out.println("Not enough fuel, waiting for another supply  ");
                continue;
            }
            job += 10;
            if (job == 100) {
                multiThreadingHomework.setFlag(false);
            }
        }
        System.out.println("Robot 5 have finished his work");
        return true;
    }
}

