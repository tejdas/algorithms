package net.lc.dp;

import java.util.Arrays;

/**
 * 1130
 * Dynamic Programming
 * Recursion
 */
public class MinCostTreeFromLeafValues {
    private int[][] memoized;
    private int[][] maxMemo;
    public int mctFromLeafValues(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        memoized = new int[arr.length][arr.length];
        maxMemo = new int[arr.length][arr.length];
        for (int i = 0; i < memoized.length; i++) {
            Arrays.fill(memoized[i], -1);
            Arrays.fill(maxMemo[i], -1);
        }
        return buildTree(arr, 0, arr.length-1);
    }

    private int buildTree(int[] array, int m, int n) {
        if (memoized[m][n] != -1) return memoized[m][n];
        if (m >= n) return 0;

        if (m == n-1) return array[m] * array[n];

        int result = Integer.MAX_VALUE;
        /**
         * Find all possible left and right subtrees
         * (m,i) and (i+1,n) for all values of i between m and n
         */
        for (int i = m; i < n; i++) {
            int res1 = buildTree(array, m, i);
            int res2 = buildTree(array, i+1, n);

            /**
             * The value of each non-leaf node is equal to the product of the
             * largest leaf value in its left and right subtree respectively.
             */
            int curNode = findMax(array, m, i) * findMax(array, i+1, n);

            result = Math.min(result, res1+res2+curNode);
        }

        //System.out.println("m: " + m + " n: " + n + " res: " + result);
        memoized[m][n] = result;
        return result;
    }

    private int findMax(int[] array, int m, int n) {
        if (maxMemo[m][n] != -1) return maxMemo[m][n];
        int maxVal = Integer.MIN_VALUE;
        for (int i = m; i <= n; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }
        maxMemo[m][n] = maxVal;
        return maxVal;
    }
}
