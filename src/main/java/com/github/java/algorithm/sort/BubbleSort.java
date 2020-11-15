package com.github.java.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author pengfei.zhao
 * @date 2020/11/15 18:24
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = new int[]{9, 7, 5, 6, 3, 1};
        bubbleSort(array);
    }

    public static void bubbleSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    System.out.println(Arrays.toString(array));
                }
            }
        }
    }
}
