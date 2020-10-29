package com.github.java.algorithm.fincco;

/**
 * 斐波那契数列
 * 求斐波那契数列的第 n 项，n <= 39。
 *
 * @author pengfei.zhao
 * @date 2020/10/28 20:18
 */
public class Solution {
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
