package com.github.java.geektime.concurrency.features.visibility;

import com.github.java.Debug;

/**
 * 可见性问题
 *
 * @author pengfei.zhao
 * @date 2020/10/22 20:27
 */
public class Visibility {
    private static long count = 0;

    public void add10k() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() {
        final Visibility visibility = new Visibility();
        final Thread t1 = new Thread(visibility::add10k);
        final Thread t2 = new Thread(visibility::add10k);

        t1.start();
        t2.start();

        // 等待t1、t2线程执行结束
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return count;
    }

    public static void main(String[] args) {
        Debug.info(calc());
    }

}
