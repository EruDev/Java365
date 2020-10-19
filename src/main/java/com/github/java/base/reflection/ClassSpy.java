package com.github.java.base.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 9:53
 */
public class ClassSpy {

    enum ClassMember {CONSTRUCT, FIELD, METHOD, CLASS, ALL}

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName(args[0]);
        System.out.format("Class:%n %s%n%n", clazz.getCanonicalName());

        Package p = clazz.getPackage();
        System.out.format("Package: %n %s%n%n", (p != null ? p.getName() : "-- No Package --"));

        for (String arg : args) {
            switch (ClassMember.valueOf(arg)) {
                case CONSTRUCT:
                    printMembers(clazz.getConstructors(), "Construct");
                    break;
                case FIELD:
                    printMembers(clazz.getFields(), "Fields");
                case METHOD:
                    printMembers(clazz.getMethods(), "Methods");
                case CLASS:
                    printClasses(clazz);
                    break;
                case ALL:
                    printMembers(clazz.getConstructors(), "Constuctors");
                    printMembers(clazz.getFields(), "Fields");
                    printMembers(clazz.getMethods(), "Methods");
                    printClasses(clazz);
                    break;
                default:
                    assert false;
            }
        }
    }

    private static void printClasses(Class<?> clazz) {
        System.out.format("%s:%n", clazz);
        Class<?>[] clss = clazz.getClasses();
        for (Class<?> czz : clss) {
            System.out.format(" %s%n", czz.getCanonicalName());
        }
        if (clss.length == 0)
            System.out.format("  -- No member interfaces, classes, or enums --%n");
        System.out.format("%n");
    }

    public static void printMembers(Member[] mbrs, String s) {
        System.out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field) {
                System.out.format(" %s%n", ((Field) mbr).toGenericString());
            } else if (mbr instanceof Constructor) {
                System.out.format("  %s%n", ((Constructor) mbr).toGenericString());
            } else if (mbr instanceof Method) {
                System.out.format("  %s%n", ((Method) mbr).toGenericString());
            }
        }
        if (mbrs.length == 0) {
            System.out.format(" -- No %s --%n", s);
        }
        System.out.format("%n");
    }
}
