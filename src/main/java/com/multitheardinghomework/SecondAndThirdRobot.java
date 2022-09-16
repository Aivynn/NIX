package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SecondAndThirdRobot implements Callable<Boolean> {

    private static final MultiThreadingHomework multiThreadingHomework = MultiThreadingHomework.getInstanceUsingDoubleLocking();

        private static CountDownLatch s;

    SecondAndThirdRobot(CountDownLatch s) {
            this.s = s;
        }

        private static AtomicInteger production = new AtomicInteger(0);


        @Override
        public Boolean call() throws Exception {
            Random random = multiThreadingHomework.getRandom();
            while (production.get() < 100) {
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
