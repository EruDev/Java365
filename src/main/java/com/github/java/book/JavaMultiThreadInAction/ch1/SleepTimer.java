package com.github.java.book.JavaMultiThreadInAction.ch1;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 20:37
 */
public class SleepTimer {

    public static int count;

    public static void main(String[] args) throws InterruptedException {
        count = args.length >= 1 ? Integer.parseInt(args[0]) : 60;
        int maintain;

        while (true) {
            maintain = countDown();
            if (0 == maintain) {
                break;
            }
            System.out.printf("Countdown: %s", maintain);
            Thread.sleep(1000);

            System.out.println("Done.");
        }
    }

    private static int countDown() {
        return count--;
    }
}
