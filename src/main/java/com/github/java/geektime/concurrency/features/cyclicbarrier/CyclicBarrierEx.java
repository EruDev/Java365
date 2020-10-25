package com.github.java.geektime.concurrency.features.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierEx 中文意思 篱栅 是一组线程之间互相等待
 *
 * @author pengfei.zhao
 * @date 2020/10/25 12:34
 */
@Slf4j
public class CyclicBarrierEx {

    private static class Worker extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                log.info(Thread.currentThread().getName() + "开始等待其他线程");
                cyclicBarrier.await();
                log.info(Thread.currentThread().getName() + "开始执行");
                // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                Thread.sleep(1000);
                log.info(Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 3;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Worker(cyclicBarrier).start();
        }
    }
}
