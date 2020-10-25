package com.github.java.geektime.concurrency.features.readwritelock;

import java.util.concurrent.locks.StampedLock;

/**
 * 写锁和悲观锁 跟ReentrantReadWriteLock 类似，非可重入锁，并且不支持条件变量
 * ReadWriteLock 支持多个线程同时读，但是当多个线个线程同时读的时候，
 * 所有的写操作会被阻塞；而 StampedLock 提供的乐观读（无锁），是允许一个线程获取写锁的，也就是说不是所有的写操作都被阻塞。
 *
 * 不能直接使用interrupt 会导致cpu100%
 * 若想中断可以使用
 * readLockInterruptibly() 和writeLockInterruptibly()
 *
 * @author pengfei.zhao
 * @date 2020/10/25 12:10
 */
public class StampedLockEx {
    private final StampedLock sl = new StampedLock();

    /**
     * 获取释放 悲观读锁
     *
     * @return obj
     */
    Object get() {
        final long stamp = sl.readLock();
        try {
            // 省略业务代码
            return new Object();
        } finally {
            sl.unlockRead(stamp);
        }
    }

    /**
     * 获取/释放 写锁
     */
    void set() {
        final long stamp = sl.writeLock();
        try {
            // 省略相关业务代码
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    static class Point {
        private int x, y;
        final StampedLock sl = new StampedLock();

        Double distanceFromOrigin() {
            // 乐观度
            long stamp = sl.tryOptimisticRead();
            // 读入局部变量
            // 读的过程可能被修改
            int curX = x;
            int curY = y;
            // 判断执行读操作期间，
            // 是否存在写操作，如果存在，
            // 则 sl.validate 返回 false
            if (!sl.validate(stamp)) {
                // 升级为悲观读锁
                stamp = sl.readLock();
                try {
                    curX = x;
                    curY = y;
                } finally {
                    // 释放悲观读锁
                    sl.unlockRead(stamp);
                }
            }

            return Math.sqrt(curX * curX + curY * curY);
        }
    }
}
