package com.github.java.geektime.concurrency.features.synchronizedcase;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * Synchronized 实现原理基于操作系统 Mutex Lock(互斥锁)实现, 所以每次获取和释放都会由用户态和内核态，jdk1.5之前性能差
 * JVM通过ACC_SYNCHRONIZED标识一个同步方法，而代码块则通过monitorenter和monitorexit指令操作monitor对象
 *
 * @author pengfei.zhao
 * @date 2020/10/24 12:17
 */
@Slf4j
public class SynchronizedExample {
    static class X {
        /**
         * 修饰非静态方法, 锁对象为当前实例对象 this
         */
        synchronized void get() {
        }

        /**
         * 修饰静态方法, 锁对象为Class对象, Class X
         */
        synchronized static void set() {
        }

        /**
         * 修饰代码块
         */
        Object obj = new Object();

        void put() {
            synchronized (obj) {
            }
        }
    }

    /**
     * 利用 Synchronized 实现原子操作
     */
    static class SafeCalc {
        long count = 0;

        synchronized void addOne() {
            count++;
        }

        synchronized long getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        final SafeCalc calc = new SafeCalc();
        List<Thread> threads = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            final Thread t = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    calc.addOne();
                }
            });
            threads.add(t);
        }
        for (Thread t : threads) {
            t.start();
        }
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(String.valueOf(calc.getCount()));
    }
}
