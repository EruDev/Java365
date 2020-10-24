package com.github.java.geektime.concurrency.features.threadstate;

import com.github.java.ThreadDumpHelper;

/**
 * 线程状态
 *
 * @author pengfei.zhao
 * @date 2020/10/24 15:21
 */
public class ThreadState {
    private static ThreadDumpHelper threadDumpHelper = new ThreadDumpHelper();

    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaiting").start();
        new Thread(new Waiting(), "Waiting").start();
        new Thread(new Blocked(), "Blocked").start();

        threadDumpHelper.tryThreadDump();
    }

    static class TimeWaiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                sleep(1000);
            }
        }
    }

    static class Waiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable{

        @Override
        public void run() {
            while (true) {
                synchronized (Blocked.class) {
                    sleep(1000);
                }
            }
        }
    }

    public static void sleep(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
