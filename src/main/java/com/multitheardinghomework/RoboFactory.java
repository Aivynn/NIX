package com.multitheardinghomework;

import java.util.Random;
import java.util.concurrent.*;

public class RoboFactory {

    private static RoboFactory instance;

    public static RoboFactory getInstanceUsingDoubleLocking(){
        if(instance == null){
            synchronized (RoboFactory.class) {
                if(instance == null){
                    instance = new RoboFactory();
                }
            }
        }
        return instance;
    }

    public Boolean getFlag() {
        return flag;
    }
    public void  setFlag(Boolean value){
        flag = value;
    }

    public Random  getRandom() {
        return random;
    }

    private static volatile boolean flag = true;

    private static final Random random = new Random();

    public static void main(String[] args) throws Exception {
        FuelContainer buffer = new FuelContainer();
        CountDownLatch latch = new CountDownLatch(2);
        CountDownLatch robotFiveLatch = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(new FirstRobot(buffer));
        pool.submit(new FifthRobot(robotFiveLatch, buffer));
        pool.submit(new SecondAndThirdRobot(latch));
        pool.submit(new SecondAndThirdRobot(latch));
        pool.submit(new FourthRobot(latch, robotFiveLatch));
        pool.shutdown();
    }
}
