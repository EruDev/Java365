package com.github.java.concurrency;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 12:58
 */
public class Drop {
    // Message sent from producer
    // to consumer.
    private String message;

    // true if consumer wait
    // for producer send message
    // false if producer wait
    // consumer to retrieve message
    private boolean empty = true;

    public synchronized String take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        empty = true;
        // notify producer
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}
