package com.github.java.reflection;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 10:13
 */
public class FieldSpy<T> {
    public boolean[][] b = {{false, false}, {true, true}};
    public String name = "Alice";
    public List<Integer> list;
    public T val;

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName(args[0]);
        Field field = clazz.getField(args[1]);
        System.out.format("Type: %s%n", field.getType());
        System.out.format("GenericType: %s%n", field.getGenericType());
    }
}
