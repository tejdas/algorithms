package net.lc.recursedfs;

import java.util.*;

/**
 * 842
 * DFS Recursion
 * Stack
 */
public class SplitArrayIntoFibSequence {
    /**
     * The integer can be atmost 10 digits.
     * @param iarray
     * @param from
     * @param to
     * @return
     */
    private int getInteger(int[] iarray , int from, int to) {
        if (to < from) return -1;

        if (to+1-from > 10) return -1;

        int sum = 0;
        for (int i = from; i <= to; i++) {
            sum = sum * 10 + iarray[i];
            if (sum < 0) return -1;
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

        Stack<Integer> stack = new Stack<>();
        int ilen = Math.min(10, len);
        /**
         * For every possible first and second number, invoke dfs() to see if we can achieve Fib seq.
         * Use DFS and stack.
         */
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
        if (desiredSum < 0) return false;

        int sum = 0;

        int i = curIndex;
        while (i < array.length) {
            sum = sum * 10 + array[i];
            i++;
            if (sum >= desiredSum) break;
            if (sum < 0) break;
        }

        if (sum < 0 || sum != desiredSum) return false;

        stack.push(sum);
        if (i == array.length) return true;

        boolean flag = dfs(array, i, sum, prev1, stack);
        if (!flag) stack.pop();
        return flag;
    }
}
