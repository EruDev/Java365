package com.github.java.collections;

import com.github.java.collections.model.Employee;
import com.github.java.collections.model.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collection 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 12:49
 */
public class CollectionDemo {
    public static void main(String[] args) {
        // List<String> list = new ArrayList<String>() JDK7 <String> 需要指定类型
        List<String> list = new ArrayList<String>(){
            {
                add("Collection");
                add("List");
                add("ArrayList");
            }
        };


        // 3 种方式遍历
        // 1. 聚合操作
        // 2. for-each
        // 3. iterators
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("XiaoFang", 6000.00, Level.PRIMARY),
                new Employee("XiaoChen", 12000.00, Level.MIDDLE),
                new Employee("XiaoZhao", 18000.00, Level.ADVANCED)
        ));

        // aggregate operations
        employees.stream().filter(e -> e.getLevel() == Level.ADVANCED)
                .forEach(e -> System.out.println(e.getName()));

        employees.parallelStream()
                .filter(e -> e.getName().contains("Xiao"))
                .forEach(System.out::println);

        System.out.println(employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", ")));

        Double total = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println(total);

        // for-each
        for (Employee employee : employees) {
            System.out.println(employee);
            // throw ConcurrentModificationException
            employees.remove(employee);
        }

        // iterators
        for (Iterator<Employee> it = employees.iterator(); it.hasNext(); ) {
            if (it.next().getName().contains("Zhao")) {
                it.remove();
            }
        }
    }
}
