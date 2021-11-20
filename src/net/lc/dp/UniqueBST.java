package net.lc.dp;

import java.util.Arrays;

/**
 * 96
 * Dynamic Programming
 * Tree
 */
public class UniqueBST {
    private int[][] memoized;
    private int n;
    public int numTrees(int n) {
        if (n == 1) return 1;
        this.n = n;

        memoized = new int[n+1][n+1];
        for (int i = 0; i < memoized.length; i++) {
            Arrays.fill(memoized[i], -1);
        }
        return numBST(1, n);
    }

    private int numBST(int left, int right) {
        if (left > right) return 0;
        if (left < 1 || right > n) return 0;

        if (memoized[left][right] != -1)
            return memoized[left][right];

        if (left == right) {
            memoized[left][right] = 1;
            return memoized[left][right];
        }

        if (left == right-1) {
            memoized[left][right] = 2;
            return memoized[left][right];
        }

        int total = 0;
        for (int k = left; k <= right; k++) {
            if (k == left) {
                total += numBST(k+1, right);
            } else if (k == right) {
                total += numBST(left, k-1);
            } else {
                int val = (numBST(left, k-1) * numBST(k + 1, right));
                //System.out.println("val: " + val);
                total += val;
            }
        }

        memoized[left][right] = total;
        return memoized[left][right];
    }
}
