package com.github.java.geektime.concurrency.features.atomic;

import com.github.java.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java实现原子操作
 * 补充CAS实现原子操作的三大问题
 * 1. ABA问题 解决方法 加上版本号1A、2B、3C java 1.5 后提供AtomicStampedReference 检查当前引用是否符合预期引用，并检查当前标志是否等于预期标志
 * 2. 自旋导致的时间开销大
 * 3. 只能保证一个共享变量的原子操作 取巧方法：多个共享变量合并成一个 AtomicReference 保证引用对象的原子性
 *
 * @author pengfei.zhao
 * @date 2020/10/23 8:08
 */
public class AtomicCounter {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private int i = 0;

    public static void main(String[] args) {
        final AtomicCounter counter = new AtomicCounter();
        List<Thread> threads = new ArrayList<>();
        final long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            final Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        counter.count();
                        counter.safeCount();
                    }
                }
            });
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ex.printStackTrace();
        }
        Debug.info("AtomicCounter i:" + counter.i);
        Debug.info("AtomicCounter atomicInteger:" + counter.atomicInteger.get());
        Debug.info("escape time: %s", System.currentTimeMillis() - start);

    }

    public void safeCount() {
        // 自旋
        for (; ; ) {
            // atomicInteger.compareAndSet()
            int val = atomicInteger.get();
            boolean flag = atomicInteger.compareAndSet(i, val);
            if (flag) {
                break;
            }
        }
    }

    /**
     * 非线程安全的计数器
     *
     * @return
     */
    public void count() {
        i++;
    }
}
