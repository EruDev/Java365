package com.github.java.algorithm.leetcode.judge_square_sum;

/**
 * 633. 两数平方和
 * <pre>
 * Input: 5
 * Output: True
 * Explanation: 1 * 1 + 2 * 2 = 5
 * </pre>
 * 题目描述：判断一个非负整数是否为两个整数的平方和。
 *
 * @author pengfei.zhao
 * @date 2020/11/3 16:17
 */
public class Solution {

    public static boolean judgeSquareSum(int target) {
        int left = 1;
        int right = (int) Math.sqrt(target);

        while (left < right) {
            int result = left * left + right * right;
            if (result > target) {
                right--;
            } else if (result == target){
                System.out.println(left);
                System.out.println(right);
                return true;
            } else {
                left++;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        judgeSquareSum(41);
    }
}
