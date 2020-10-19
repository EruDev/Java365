package com.github.java.collections;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Queue 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 16:54
 */
public class QueueDemo {
    public static void main(String[] args) {

    }

    static class CountDown {
        public static void main(String[] args) throws InterruptedException {
            int time = Integer.parseInt(args[0]);
            Queue<Integer> queue = new LinkedList<>();

            for (int i = time; i >= 0; i--) {
                queue.add(i);
            }

            while (!queue.isEmpty()) {
                System.out.println(queue.remove());
                Thread.sleep(1000);
            }
        }
    }
}
