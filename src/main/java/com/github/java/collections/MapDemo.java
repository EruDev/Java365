package com.github.java.collections;

import com.github.java.collections.model.Department;
import com.github.java.collections.model.Employee;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Map 相关例子
 *
 * @author pengfei.zhao
 * @date 2020/10/11 18:37
 */
public class MapDemo {

    public static class Anagrams {
        public static void main(String[] args) {
            int minGroupSize = Integer.parseInt(args[1]);

            // Read words from file and put into a simulated multimap
            Map<String, List<String>> m = new HashMap<String, List<String>>();

            try {
                Scanner s = new Scanner(new File(args[0]));
                while (s.hasNext()) {
                    String word = s.next();
                    String alpha = alphabetize(word);
                    List<String> l = m.get(alpha);
                    if (l == null)
                        m.put(alpha, l=new ArrayList<String>());
                    l.add(word);
                }
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            }

            // Print all permutation groups above size threshold
            for (List<String> l : m.values())
                if (l.size() >= minGroupSize)
                    System.out.println(l.size() + ": " + l);
        }

        private static String alphabetize(String s) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }

    static class Freq {
        public static void main(String[] args) {
            Map<String, Integer> map = new HashMap<>();
            for (String arg : args) {
                map.put(arg, arg == null ? 1 : map.get(arg) + 1);
            }

            System.out.println(map.size() + " distinct words:");
            System.out.println(map);
        }
    }

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>(List.of(
                new Employee("XiaoFang", Department.DEVELOP),
                new Employee("XiaoChen", Department.PRODUCT),
                new Employee("XiaoZhao", Department.DEVELOP),
                new Employee("XiaoTest", Department.TEST)
        ));

        Map<Department, List<Employee>> empMap = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(empMap);

        Map<Department, Double> totalByDept = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)));
    }
}
