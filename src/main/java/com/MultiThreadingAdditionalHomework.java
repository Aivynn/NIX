package com;

public class MultiThreadingAdditionalHomework {

    private static int LIMIT = 50;
    private static int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {
        ThreadCreator t = new ThreadCreator();
        threadRecursion(t, 0);

    }


    public static class ThreadCreator extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from thread + " + Thread.currentThread().getName());
        }
    }

    public static void threadRecursion(Thread thread, int threadNumber) throws InterruptedException {
        if (threadNumber != THREAD_NUMBER) {
            threadRecursion(new ThreadCreator(), ++threadNumber);
        }
        if (threadNumber == LIMIT) {
            thread.start();
            thread.join();
            --LIMIT;
        }

    }


}
