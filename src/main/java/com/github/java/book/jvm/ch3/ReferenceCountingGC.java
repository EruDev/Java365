package com.github.java.book.jvm.ch3;

/**
 * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 * @author pengfei.zhao
 * @date 2020/10/30 8:22
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();
    }

}
