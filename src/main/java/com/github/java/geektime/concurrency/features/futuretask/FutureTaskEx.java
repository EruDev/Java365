package com.github.java.geektime.concurrency.features.futuretask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author pengfei.zhao
 * @date 2020/10/26 21:17
 */
@Slf4j
public class FutureTaskEx {

    /**
     * FutureTask 由线程池执行
     */
    public static void exeForPool() {
        final FutureTask<Integer> task = new FutureTask<>(() -> 1 + 2);
        final ExecutorService service = Executors.newFixedThreadPool(1);

        try {
            service.submit(task);
            final Integer result = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    /**
     * FutureTask由线程处理
     */
    public static void exeForThread() {
        final FutureTask<Integer> task = new FutureTask<>(() -> 1 + 2);
        Thread t = new Thread(task);
        t.start();

        try {
            Integer result = task.get();
            log.info(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用FutureTask实现烧水泡茶
     */
    private static void fireWater() {
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);
        t1.start();
        t2.start();

        try {
            System.out.println(ft1.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class T1Task implements Callable<String> {
        FutureTask<String> ft2;

        public T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1: 洗水壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1: 烧开水...");
            TimeUnit.SECONDS.sleep(15);
            // 获取 T2 线程的茶叶
            String tf = ft2.get();
            System.out.println("T1: 拿到茶叶:"+tf);

            System.out.println("T1: 泡茶...");
            return " 上茶:" + tf;
        }
    }

    static class T2Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("T2: 洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2: 洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2: 拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return " 龙井 ";
        }
    }
}
