package net.lc;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/split-array-largest-sum/
 * Two ways of doing it:
 * 1. Dynamic Programming
 * 2. Greedy + Binary-Search (on all possible sums)
 */
public class SplitArrayLargestSum {
    private int[][] result;

    public int splitArray2(int[] array, int m) {
        if (array == null || array.length == 0) return 0;
        result = new int[array.length][m+1];

        for (int i = 0; i < result.length; i++) {
            Arrays.fill(result[i], -1);
        }
        this.array = array;
        return findOptimum(array.length-1, m);
    }

    private int findOptimum(int index, int splitCount) {
        //System.out.println("findOptimum: " + index + ":" + splitCount);

        if (index < 0 || splitCount <= 0) return Integer.MAX_VALUE;

        if (splitCount == 1) {
            int sum = 0;
            for (int i = 0; i <= index; i++) {
                sum += array[i];
            }

            result[index][splitCount] = sum;
            return sum;
        }

        if (result[index][splitCount] != -1) {
            return result[index][splitCount];
        }

        int sum = 0;

        int maxSum = Integer.MAX_VALUE;
        for (int i = index; i >= 0; i--) {
            sum += array[i];

            int prevSum = findOptimum(i-1, splitCount-1);
            maxSum = Math.min(maxSum, Math.max(sum, prevSum));
        }

        result[index][splitCount] = maxSum;
        return maxSum;
    }

    private int[] array;

    public int splitArray(int[] array, int m) {
        if (array == null || array.length == 0) return 0;
        this.array = array;

        int totalSum = 0;
        int largestVal = Integer.MIN_VALUE;

        for (int val : array) {
            totalSum += val;
            largestVal = Math.max(largestVal, val);
        }

        /**
         * Binary-search on all possible values between largest single number, and total sum
         */
        return search(largestVal, totalSum, m);
    }

    private int search(int min, int max, int m) {
        int left = min;
        int right = max;

        int minIsPossible = right;
        while (left <= right) {
            int mid = (left + right)/2;

            boolean flag = isPossible(array, mid, m);
            if (flag) {
                minIsPossible = Math.min(mid, minIsPossible);
                right = mid-1;
            } else {
                left = mid+1;
            }
        }

        return minIsPossible;
    }

    /**
     * Checks to see if it is possible to split the array into at-most m sub-arrays
     * with the none of the sub-arrays exceeding the limit
     * @param array
     * @param limit
     * @param m
     * @return
     */
    private boolean isPossible(int[] array, int limit, int m) {
        int subArrayCount = 0;
        int runningSum = 0;

        int startIndex = 0;
        int index;
        for (index = 0; index < array.length; index++) {
            runningSum += array[index];
            if (runningSum < limit) continue;

            subArrayCount++;
            if (subArrayCount > m) return false;

            if (runningSum == limit) {
                runningSum = 0;
                startIndex = index+1;
            } else {
                runningSum = array[index];
                startIndex = index;
            }
        }
        if (startIndex < array.length)
            subArrayCount++;
        return (subArrayCount <= m);
    }

    public static void main(String[] args) {

        {
            int[] array = { 7, 2, 5, 10, 8 };
            System.out.println(new SplitArrayLargestSum().splitArray(array, 2));
        }

        {
            int[] array = { 1,2,3,4,5 };
            System.out.println(new SplitArrayLargestSum().splitArray(array, 2));
        }

        {
            int[] array = { 1,4,4 };
            System.out.println(new SplitArrayLargestSum().splitArray(array, 3));
        }

        {
            int[] array = {10,5,13,4,8,4,5,11,14,9,16,10,20,8};
            System.out.println(new SplitArrayLargestSum().splitArray(array, 8));
            //System.out.println(new SplitArrayLargestSum().isPossible(array, 24, 8));
        }

        {
            int[] array = {1,2,3,4,5,6,7,8,9};
            System.out.println(new SplitArrayLargestSum().splitArray(array, 6));
            //System.out.println(new SplitArrayLargestSum().isPossible(array, 24, 8));
        }
    }
}
