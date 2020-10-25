package com.github.java.geektime.concurrency.features.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link CountDownLatch} 计数器 主要用来解决一个线程等待多个线程的场景
 *
 * @author pengfei.zhao
 * @date 2020/10/25 12:27
 */
public class CountDownLatchEx {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        service.execute(new Thread(() -> {
            System.out.println(1);
            countDownLatch.countDown();
        }));

        service.execute(new Thread(() -> {
            System.out.println(2);
            countDownLatch.countDown();
        }));

        // 等待两个线程执行完成
        countDownLatch.await();
        service.shutdown();
    }
}
