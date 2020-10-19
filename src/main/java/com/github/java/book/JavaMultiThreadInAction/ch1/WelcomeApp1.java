package com.github.java.book.JavaMultiThreadInAction.ch1;

/**
 * 实现 {@link Runnable} 接口
 *
 * @author pengfei.zhao
 * @date 2020/10/18 16:36
 */
public class WelcomeApp1 {
    public static void main(String[] args) {
        Thread t = new Thread(new WelcomeRunnable());
        t.start();

        System.out.printf("2.Welcome I'm %s.%n", Thread.currentThread().getName());
    }
}

class WelcomeRunnable implements Runnable{

    @Override
    public void run() {
        System.out.printf("1.Welcome I'm %s.%n", Thread.currentThread().getName());
    }
}
