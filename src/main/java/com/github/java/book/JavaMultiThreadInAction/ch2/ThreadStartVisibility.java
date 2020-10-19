package com.github.java.book.JavaMultiThreadInAction.ch2;

import com.github.java.book.JavaMultiThreadInAction.util.Tools;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 14:32
 */
public class ThreadStartVisibility {
    // 线程间共享的变量
    static int data = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Tools.randomPause(50);
                // 线程启动前, 修改data的值是能感知到的
                System.out.println(data);
            }
        });

        // 在子线程启动前修改data的值
        data = 1;
        thread.start();

        Tools.randomPause(50);

        data = 2;
    }
}
