package com.github.java.algorithm.jump_floor_II;

/**
 * # 10.4 变态跳台阶
 *
 * ## 题目链接
 *
 * [NowCoder](https://www.nowcoder.com/practice/22243d016f6b47f2a6928b4313c85387?tpId=13&tqId=11162&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)
 *
 * ## 题目描述
 *
 * 一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级... 它也可以跳上 n 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 *
 * <div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/cd411a94-3786-4c94-9e08-f28320e010d5.png" width="380px"> </div><br>
 *
 * ### 数学推导
 *
 * 跳上 n-1 级台阶，可以从 n-2 级跳 1 级上去，也可以从 n-3 级跳 2 级上去...，那么
 *
 * ```
 * f(n-1) = f(n-2) + f(n-3) + ... + f(0)
 * ```
 *
 * 同样，跳上 n 级台阶，可以从 n-1 级跳 1 级上去，也可以从 n-2 级跳 2 级上去... ，那么
 *
 * ```
 * f(n) = f(n-1) + f(n-2) + ... + f(0)
 * ```
 *
 * 综上可得
 *
 * ```
 * f(n) - f(n-1) = f(n-1)
 * ```
 *
 * 即
 *
 * ```
 * f(n) = 2*f(n-1)
 * ```
 *
 * @author pengfei.zhao
 * @date 2020/10/29 16:22
 */
public class Solution {

    public static int jumpFloorII(int n) {
        return (int) Math.pow(2, n-1);
    }
}
