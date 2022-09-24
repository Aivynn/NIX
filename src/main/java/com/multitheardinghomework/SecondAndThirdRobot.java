package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Thread.sleep;
public class SecondAndThirdRobot implements Callable<Boolean> {

    private static final RoboFactory roboFactory = RoboFactory.getInstanceUsingDoubleLocking();


    private static CountDownLatch s;

    SecondAndThirdRobot(CountDownLatch s) {
        this.s = s;
    }

    private static AtomicInteger production = new AtomicInteger(0);


    @Override
    public Boolean call() throws Exception {
        Random random = roboFactory.getRandom();
        while (production.get() < 100) {
            sleep(1000);
            int job = random.nextInt(10, 20);
            production.set(production.get() + job);
            System.out.println(job + " " + Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println(production.get());
        }
        System.out.println(Thread.currentThread() + "have finished his work");
        s.countDown();
        return true;
    }
}
