package net.lc;

import java.util.*;

/**
 * 842
 * https://leetcode.com/problems/split-array-into-fibonacci-sequence/submissions/
 * Back-tracking (DFS stack)
 */
public class SplitArrayIntoFibSequence {
    private int getInteger(int[] iarray, int from, int to) {
        if (to < from)
            return -1;

        if (to + 1 - from > 10) {
            // will cause overflow
            return -1;
        }

        int sum = 0;
        for (int i = from; i <= to; i++) {
            sum = sum * 10 + iarray[i];
            if (sum < 0) //encountered overflow
                return -1;
        }
        return sum;
    }

    public List<Integer> splitIntoFibonacci(String S) {
        char[] sarray = S.toCharArray();
        int[] array = new int[sarray.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = Character.digit(sarray[i], 10);
        }

        int len = array.length;

        /**
         * pick all possible first and second values, and start
         * recursion/back-track from there, using DFS/stack.
         */
        Stack<Integer> stack = new Stack<>();
        int ilen = Math.min(10, len);
        for (int i = 0; i < ilen; i++) {
            int firstVal = getInteger(array, 0, i);
            if (firstVal == -1)
                break;
            stack.push(firstVal);

            int jlen = Math.min(len, i + 10);
            for (int j = i + 1; j < jlen; j++) {
                int secondVal = getInteger(array, i + 1, j);
                if (secondVal == -1)
                    break;

                stack.push(secondVal);

                if (dfs(array, j + 1, secondVal, firstVal, stack)) {
                    List<Integer> list = new ArrayList<>();
                    for (int val : stack)
                        list.add(val);
                    return list;
                } else {
                    stack.pop();
                }
            }

            stack.pop();

            if (sarray[0] == '0') {
                break;
            }
        }

        return new ArrayList<>();
    }

    private boolean dfs(int[] array, int curIndex, int prev1, int prev2, Stack<Integer> stack) {

        int desiredSum = prev1 + prev2;
        if (desiredSum < 0)
            return false;

        int sum = 0;

        int i = curIndex;
        while (i < array.length) {
            sum = sum * 10 + array[i];
            i++;
            if (sum >= desiredSum)
                break;
            if (sum < 0)
                break;

        }

        if (sum < 0 || sum != desiredSum)
            return false;

        stack.push(sum);
        if (i == array.length)
            return true;

        boolean flag = dfs(array, i, sum, prev1, stack);
        if (!flag)
            stack.pop();
        return flag;
    }

    public static void main(String[] args) {
        {
            List<Integer> res = new SplitArrayIntoFibSequence().splitIntoFibonacci("123456579");
            if (res.size() > 0) {
                System.out.println(Arrays.toString(res.toArray(new Integer[res.size()])));
            } else {
                System.out.println("[]");
            }
        }

        {
            List<Integer> res = new SplitArrayIntoFibSequence().splitIntoFibonacci("11235813");
            if (res.size() > 0) {
                System.out.println(Arrays.toString(res.toArray(new Integer[res.size()])));
            } else {
                System.out.println("[]");
            }
        }
    }
}
