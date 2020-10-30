package com.github.java.book.jvm.ch2;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示堆出现OOM的情况
 *
 * @author pengfei.zhao
 * @date 2020/10/29 8:36
 */
public class HeapOOM {
    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
