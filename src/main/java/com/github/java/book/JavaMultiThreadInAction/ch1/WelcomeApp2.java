package com.github.java.book.JavaMultiThreadInAction.ch1;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 16:42
 */
public class WelcomeApp2 {
    public static void main(String[] args) {
        Thread welcomeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("1.Welcome I'm %s.%n", Thread.currentThread().getName());
            }
        });
        welcomeThread.start();
        // welcomeThread#run 方法被执行了两次, 一次是JVM直接调用执行, 此时welcomeThread run是运行在自己的线程中, 也就是 Thread-0
        // 另一次是在 main 线程中
        welcomeThread.run();
        System.out.printf("2.Welcome I'm %s.%n", Thread.currentThread().getName());
    }
}
