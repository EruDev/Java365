package com.github.java.book.jvm.ch3;

/**
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author pengfei.zhao
 * @date 2020/10/31 9:53
 */
public class Allocation {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testAllocation();
    }

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次 Minor GC
    }
}
