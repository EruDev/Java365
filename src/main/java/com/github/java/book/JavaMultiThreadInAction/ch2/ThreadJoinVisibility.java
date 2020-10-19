package com.github.java.book.JavaMultiThreadInAction.ch2;

import com.github.java.book.JavaMultiThreadInAction.util.Tools;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 14:39
 */
public class ThreadJoinVisibility {
    // 线程间共享的变量
    static int data = 0;
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Tools.randomPause(50);
                data = 1;
            }
        });
        thread.start();

        try {
            // 等待子线程执行完毕
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }
}
