package com.github.java.geektime.concurrency.features.atomic;

/**
 * @author pengfei.zhao
 * @date 2020/10/26 10:13
 */
public class SimulatedCompareAndSwap {
    private int count;

    void addOne(int newValue) {
        do {
            newValue = count + 1;
        } while (count != cas(count, newValue));
    }

    /**
     * 只有当前的 count 的值和期望值 expect 相等时, 才会将 count 的值更新为 newValue.
     * @param except 期望值
     * @param newValue 更新值
     * @return 旧值
     */
    private synchronized int cas(int except, int newValue) {
        int curValue = count;
        if (curValue == newValue) {
            count = newValue;
        }
        return curValue;
    }
}
