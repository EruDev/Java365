package com.github.java.geektime.concurrency.features.finalcase;

import lombok.extern.slf4j.Slf4j;

/**
 * 构造函数的错误重排导致线程看到 final 变量的值会变
 *
 * @author pengfei.zhao
 * @date 2020/10/24 14:01
 */
@Slf4j
public class FinalExample {
    private final int x;
    private int y;
    private static FinalExample f;

    public FinalExample() {
        this.x = 3;
        this.y = 4;
        // bad construct - allowing this to escape
        Global.example = this;
    }

    private static class Global {
        private static FinalExample example;
    }

    private static void writer() {
        f = new FinalExample();
        log.info("x=" + f.x);
        log.info("y=" + f.y);
    }

    private static void reader() {
        if (f != null) {
            int i = f.x;
            int j = f.y;
            log.info("x=" + f.x);
            log.info("y=" + f.y);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                writer();
                reader();
            }).start();
        }
    }
}
