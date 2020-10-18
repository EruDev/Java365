package com.github.java.book.JavaMultiThreadInAction.ch1;

import com.github.java.book.JavaMultiThreadInAction.util.Tools;


/**
 * @author pengfei.zhao
 * @date 2020/10/18 16:58
 */
public class ThreadCreationCmp {

    public static void main(String[] args) {
        Thread t;
        CounterTask counterTask = new CounterTask();

        // 获取处理器数量
        final int processors = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < 2 * processors; i++) {
            // 以子类的方式创建线程
            t = new CounterThread();
            t.start();
        }

        for (int i = 0; i < 2 * processors; i++) {
            // 直接创建线程
            t = new Thread(counterTask);
            t.start();
        }
    }

    static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    static class CounterThread extends Thread {
        Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.printf("CounterThread count: %s.%n", counter.getCount());
        }
    }

    static class CounterTask implements Runnable {
        Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.printf("CounterTask count: %s.%n", counter.getCount());
        }
    }

    public static void doSomething() {
        Tools.randomPause(80);
    }
}
