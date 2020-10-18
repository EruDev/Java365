package com.github.java.concurrency;

/**
 * consumer producer demo.
 * {@link Consumer} {@link Producer} {@link Drop}
 *
 * @author pengfei.zhao
 * @date 2020/10/17 13:07
 */
public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Drop drop = new Drop();
        new Thread(new Producer(drop)).start();
        new Thread(new Consumer(drop)).start();
    }
}
