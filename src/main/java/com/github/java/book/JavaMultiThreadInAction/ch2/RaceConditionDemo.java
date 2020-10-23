package com.github.java.book.JavaMultiThreadInAction.ch2;

import com.github.java.Tools;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 20:57
 */
public class RaceConditionDemo {
    public static void main(String[] args) {
        final int processors = args.length > 0 ? Integer.parseInt(args[0]) :
                Runtime.getRuntime().availableProcessors();
        final Thread[] threads = new Thread[processors];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new WorkerThread(i, 10);
        }

        // 等待所有线程创建完毕后, 再一次性启动, 以便这些线程尽可能地在同一时间允许
        for (Thread thread : threads) {
            thread.start();
        }

    }

    // 模拟业务线程
    static class WorkerThread extends Thread {
        private final int requestCount;

        public WorkerThread(int id, int requestCount) {
            super("worker-" + id);
            this.requestCount = requestCount;
        }

        @Override
        public void run() {
            int i = requestCount;
            String requestId;
            final RequestIDGenerator idGenerator = RequestIDGenerator.getInstance();
            while (i-- > 0) {
                requestId = idGenerator.nextId();
                processRequest(requestId);
            }
        }

        // 模拟请求耗时
        private void processRequest(String requestId) {
            Tools.randomPause(50);
            System.out.printf("%s got requestID: %s %n",
                    Thread.currentThread().getName(), requestId);
        }
    }
}
