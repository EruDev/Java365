package com.github.java.collections;

import com.github.java.collections.model.Name;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Comparable 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 19:59
 */
public class ComparableDemo {

    public static void main(String[] args) {
        Name nameArray[] = {
                new Name("John", "Smith"),
                new Name("Karl", "Ng"),
                new Name("Jeff", "Smith"),
                new Name("Tom", "Rich")
        };

        List<Name> names = Arrays.asList(nameArray);
        Collections.sort(names);
        System.out.println(names);
    }
}
