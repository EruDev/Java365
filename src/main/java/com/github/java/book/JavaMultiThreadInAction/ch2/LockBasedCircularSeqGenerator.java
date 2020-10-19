package com.github.java.book.JavaMultiThreadInAction.ch2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 20:36
 */
public class LockBasedCircularSeqGenerator implements CircularSeqGenerator {
    private short sequence = -1;
    private Lock lock = new ReentrantLock();

    @Override
    public short nextSequence() {
        lock.lock();
        try {
            if (sequence >= 999) {
                sequence = 0;
            } else {
                sequence++;
            }
        } finally {
            lock.unlock();
        }
        return sequence;
    }
}
