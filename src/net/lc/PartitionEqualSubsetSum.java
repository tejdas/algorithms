package net.lc;

import java.util.Arrays;

/**
 * 416
 * Dynamic Programming
 * Recursion
 */
public class PartitionEqualSubsetSum {
    private int[][] res;
    public boolean canPartition(int[] array) {
        if (array == null || array.length == 0) return true;

        int sum = 0;
        for (int val : array) {
            sum += val;
        }

        if (sum %2 == 1) return false;

        return findSum(array, sum/2);
    }

    private boolean findSum(int[] array, int targetSum) {
        res = new int[array.length][targetSum+1];
        for (int i = 0; i < res.length; i++) {
            Arrays.fill(res[i], -1);
        }

        return (findSumRecurse(array, 0, targetSum)==1);
    }

    private int findSumRecurse(int[] array, int index, int targetSum) {
        if (index == array.length) return 0;
        if (targetSum < 0) return 0;
        if (res[index][targetSum] != -1) {
            return res[index][targetSum];
        }

        if (array[index] == targetSum) {
            res[index][targetSum] = 1;
            return 1;
        }

        int val1 = findSumRecurse(array, index+1, targetSum);
        if (val1 == 1) {
            res[index][targetSum] = 1;
            return 1;
        }

        int val2 = findSumRecurse(array, index+1, targetSum-array[index]);
        if (val2 == 1) {
            res[index][targetSum] = 1;
            return 1;
        }

        res[index][targetSum] = 0;
        return 0;
    }
}
