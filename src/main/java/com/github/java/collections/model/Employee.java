package com.github.java.collections.model;

import lombok.*;

/**
 * 雇员实体
 *
 * @author pengfei.zhao
 * @date 2020/10/11 13:14
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    /**
     * 姓名
     */
    private String name;

    /**
     * 薪水
     */
    private Double salary;

    /**
     * 级别
     */
    private Level level;

    /**
     * 部门
     */
    private Department department;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Employee(String name, Double salary, Level level) {
        this.name = name;
        this.salary = salary;
        this.level = level;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(salary, employee.salary) &&
                level == employee.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, level);
    }*/
}
