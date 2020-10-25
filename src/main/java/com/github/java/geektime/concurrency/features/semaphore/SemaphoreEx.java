package com.github.java.geektime.concurrency.features.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * {@link Semaphore} 实现限流器, 池化资源, 例如数据库连接池等
 *
 * @author pengfei.zhao
 * @date 2020/10/25 10:04
 */
@Slf4j
public class SemaphoreEx {

    /**
     * 实现限流器
     *
     * @param <T>
     * @param <R>
     */
    static class ObjPool<T, R> {
        final List<T> pool;
        final Semaphore semaphore;

        ObjPool(int size, T t) {
            pool = new Vector<>();
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            semaphore = new Semaphore(size);
        }

        /**
         * 利用对象池的对象，调用 func
         * @param func func
         * @return R
         * @throws InterruptedException ex
         */
        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            semaphore.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ObjPool<String, Object> pool = new ObjPool<>(10, "Worker");
        pool.exec(t -> {
            log.info(t);
            return t.toString();
        });
    }
}
