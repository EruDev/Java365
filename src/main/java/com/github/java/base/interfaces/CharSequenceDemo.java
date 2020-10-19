package com.github.java.interfaces;

/**
 * CharSequenceDemo presents a String value -- backwards.
 *
 * @author pengfei.zhao
 * @date 2020/10/13 21:02
 */
public class CharSequenceDemo implements CharSequence {
    private String s;

    public CharSequenceDemo(String s) {
        this.s = s;
    }

    @Override
    public int length() {
        return s.length();
    }

    public int fromEnd(int index) {
        return s.length() - 1 - index;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || (index >= length())) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return s.charAt(fromEnd(index));
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end >= length()) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(start);
        }
        StringBuilder sub = new StringBuilder(s.subSequence(start, end));
        return sub.reverse();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    private static int random(int max) {
        return (int) Math.round(Math.random() * (max + 1));
    }

    public static void main(String[] args) {
        CharSequenceDemo s =
                new CharSequenceDemo("Write a class that implements the CharSequence interface found in the java.lang package.");

        //exercise charAt() and length()
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i));
        }

        System.out.println("");

        //exercise subSequence() and length();
        int start = random(s.length() - 1);
        int end = random(s.length() - 1 - start) + start;
        System.out.println(s.subSequence(start, end));

        //exercise toString();
        System.out.println(s);

    }
}
