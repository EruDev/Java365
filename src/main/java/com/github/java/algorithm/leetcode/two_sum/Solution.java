package com.github.java.algorithm.leetcode.two_sum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. 有序数组的 Two Sum
 *
 * <pre>
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2
 * </pre>
 * <p>
 * 题目描述：在有序数组中找出两个数，使它们的和为 target。
 *
 * @author pengfei.zhao
 * @date 2020/11/2 14:28
 */
public class Solution {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 26;

        twoSum(nums, target);
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null) return null;

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int tmp = target - num;
            if (list.contains(tmp)) {
                map.put(num, list.indexOf(num) + 1);
                map.put(target - num, list.indexOf(target - num) + 1);
            }
        }
        System.out.println(map);
        return new int[]{0};
    }

    public static int[] twoSum2(int[] nums, int target) {
        if (nums == null) return null;
        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                return new int[]{i + 1, j + 1};
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }
}
