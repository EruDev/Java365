package com.github.java.collections;

import com.github.java.collections.model.Employee;
import com.github.java.collections.model.Level;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Set 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 13:58
 */
public class SetDemo {
    public static void main(String[] args) {
        findDups2(args);
        findDups(args);
        bulkOperations();

        Set<Employee> employeeSet = new HashSet<Employee>() {
            {
                add(new Employee("XiaoFang", 6000.00, Level.PRIMARY));
                add(new Employee("XiaoChen", 12000.00, Level.MIDDLE));
                add(new Employee("XiaoZhao", 18000.00, Level.ADVANCED));
                add(new Employee("XiaoZhao", 18000.00, Level.ADVANCED));
            }
        };

        TreeSet<String> set = employeeSet.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);

    }

    public static void findDups(String[] args) {
        Set<String> distinctWords = Arrays.asList(args)
                .stream()
                .collect(Collectors.toSet());
        System.out.println(distinctWords.size() + "distinct words : " + distinctWords);
    }

    /**
     * 交集、并集、差集
     */
    public static void bulkOperations() {
        Set<String> s1 = new HashSet<String>() {
            {
                add("a");
                add("b");
                add("c");
            }
        };
        Set<String> s2 = new HashSet<String>() {
            {
                add("b");
                add("c");
                add("d");
            }
        };

        Set<String> union = new HashSet<>(s1);
        union.addAll(s2);
        System.out.println(union);

        // 并集
        Set<String> intersection = new HashSet<>(s1);
        intersection.retainAll(s2);
        System.out.println(intersection);

        // 差集
        Set<String> difference = new HashSet<>(s1);
        difference.removeAll(s2);
        System.out.println(difference);
    }

    public static void findDups2(String[] args) {
        Set<String> uniques = new HashSet<>();
        Set<String> dups = new HashSet<>();

        for (String item : args) {
            if (!uniques.add(item)) {
                dups.add(item);
            }
        }

        uniques.removeAll(dups);

        System.out.println("uniques : " + uniques);
        System.out.println("dups : " + dups);
    }
}
