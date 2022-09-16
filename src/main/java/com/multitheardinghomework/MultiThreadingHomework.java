package com.multitheardinghomework;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.*;

public class MultiThreadingHomework {

    private static MultiThreadingHomework instance;

    public static MultiThreadingHomework getInstanceUsingDoubleLocking(){
        if(instance == null){
            synchronized (MultiThreadingHomework.class) {
                if(instance == null){
                    instance = new MultiThreadingHomework();
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
        Buffer buffer = new Buffer();
        MultiThreadingHomework multiThreadingHomework = new MultiThreadingHomework();
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
