package net.lc;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/counting-bits/submissions/
 */
public class CountBits {
    public int[] countBits(int num) {
        if (num == 0) return new int[] {0};
        if (num == 1) return new int[] {0, 1};

        num++;
        int[] array = new int[num];
        array[0] = 0;
        array[1] = 1;



        int curPow = 1;
        int curIndex = 2;

        while (curIndex < num) {
            int untilIndex = curIndex*2;
            int offset = (int) Math.pow(2, curPow);
            while(curIndex < untilIndex) {
                array[curIndex] = 1 + array[curIndex-offset];
                curIndex++;
                if (curIndex == num) break;
            }
            curPow++;
        }

        return array;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CountBits().countBits(8)));
    }
}
