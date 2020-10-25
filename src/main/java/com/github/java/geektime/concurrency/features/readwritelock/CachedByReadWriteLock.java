package com.github.java.geektime.concurrency.features.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实现缓存
 *
 * {@link ReentrantReadWriteLock}只支持锁的降级 不支持锁升级 并且只有写锁才能创建条件变量
 * 读写锁允许多个线程读共享变量，当一个线程在写共享变量的时候，是不允许其他线程执行写操作和读操作的
 *
 * @author pengfei.zhao
 * @date 2020/10/25 11:50
 */
public class CachedByReadWriteLock<K, V> {
    private final Map<K, V> m = new HashMap<>();
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final Lock readLock = rwl.readLock();
    /**
     * 写锁
     */
    private final Lock writeLock = rwl.writeLock();

    /**
     * 写
     *
     * @param key 键
     * @param v   值
     * @return 值
     */
    V put(K key, V v) {
        writeLock.lock();
        try {
            m.put(key, v);
        } finally {
            writeLock.unlock();
        }
        return v;
    }

    /**
     * 读
     *
     * @param key 键
     * @return 值
     */
    V get1(K key) {
        readLock.lock();
        try {
            return m.get(key);
        } finally {
            readLock.unlock();
        }
    }

    V get2(K key) {
        V v = null;
        readLock.lock();
        try {
            v = m.get(key);
        } finally {
            readLock.unlock();
        }
        // 缓存中存在，返回
        if (v != null) {
            return v;
        }

        // 缓存中不存在，查询数据库
        writeLock.lock();
        try {
            // 再次验证
            // 并发情况下其他线程可能已经查询过数据库
            v = m.get(key);
            if (v == null) {
                m.put(key, v);
            }
        } finally {
            writeLock.unlock();
        }
        return v;
    }
}
