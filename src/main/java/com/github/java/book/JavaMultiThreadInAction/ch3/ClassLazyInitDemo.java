package com.github.java.book.JavaMultiThreadInAction.ch3;

import com.github.java.Debug;

/**
 * @author pengfei.zhao
 * @date 2020/10/20 14:02
 */
public class ClassLazyInitDemo {
    public static void main(String[] args) {
        Debug.info(Collaborator.class.hashCode());
        Debug.info(Collaborator.number);
        Debug.info(Collaborator.flag);
    }

    static class Collaborator {
        static int number = 1;
        static boolean flag = true;
        static {
            Debug.info("Collaborator initializing...");
        }
    }
}
