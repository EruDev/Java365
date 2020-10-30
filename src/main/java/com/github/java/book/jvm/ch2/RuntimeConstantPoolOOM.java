package com.github.java.book.jvm.ch2;

import java.util.HashSet;
import java.util.Set;

/**
 * -XX:PermSize=6M -XX:MaxPermSize=6M
 *
 * @author pengfei.zhao
 * @date 2020/10/30 7:51
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
