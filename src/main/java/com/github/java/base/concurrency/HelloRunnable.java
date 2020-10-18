package com.github.java.concurrency;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 9:20
 */
public class HelloRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from a thread");
    }

    public static void main(String[] args) {
        new Thread(new HelloRunnable()).start();
    }
}
