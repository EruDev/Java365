package com.github.java.book.jvm.ch3;

/**
 * -verbose:gc -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 *
 * @author pengfei.zhao
 * @date 2020/10/31 10:09
 */
public class BigObj {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testPretenureSizeThreshold();
    }

    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }
}
