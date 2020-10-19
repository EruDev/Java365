package com.github.java.book.JavaMultiThreadInAction.ch1;

/**
 * 继承 {@link Thread}
 *
 * @author pengfei.zhao
 * @date 2020/10/18 16:31
 */
public class WelcomeApp {
    public static void main(String[] args) {
        WelcomeThread t = new WelcomeThread();
        t.start();

        // 输出当前线程
        System.out.printf("2.Welcome I'm %s.%n", Thread.currentThread().getName());
    }
}

class WelcomeThread extends Thread {
    @Override
    public void run() {
        System.out.printf("1.Welcome I'm %s.%n", Thread.currentThread().getName());
    }
}
