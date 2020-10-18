package com.github.java.concurrency;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 9:36
 */
public class SleepMessages {
    public static void main(String[] args) throws InterruptedException {
        String[] importantInfo = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (int i = 0; i < importantInfo.length; i++) {
            Thread.sleep(3000);
            System.out.println(importantInfo[i]);
        }
    }
}
