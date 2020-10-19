package com.github.java.concurrency;

import java.util.Random;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 13:06
 */
public class Consumer implements Runnable{
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String message = drop.take(); !message.equals("DONE"); message = drop.take()){
            System.out.format("Receive message: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
