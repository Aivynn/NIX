package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class FourthRobot  implements Callable<Boolean> {

    private static final MultiThreadingHomework multiThreadingHomework = MultiThreadingHomework.getInstanceUsingDoubleLocking();

        private final CountDownLatch s;
        private final CountDownLatch robotFiveLatch;

    FourthRobot(CountDownLatch s, CountDownLatch robotFiveLatch) {
            this.s = s;
            this.robotFiveLatch = robotFiveLatch;
    }

        @Override
        public Boolean call() throws Exception {
            s.await();
            int production = 0;
            boolean flag = false;
            Random random = multiThreadingHomework.getRandom();
            while (!flag) {
                production += random.nextInt(25, 35);
                if (random.nextInt(1, 100) < 30) {
                    production = 0;
                    System.out.println("Robot 4 broke microscheme");
                }
                if (production >= 100) {
                    flag = true;
                }
                System.out.println("Robot 4 created " + production);
                Thread.sleep(1000);
            }
            System.out.println("Robot 4 have finished his work");
            robotFiveLatch.countDown();
            return true;
        }
    }
