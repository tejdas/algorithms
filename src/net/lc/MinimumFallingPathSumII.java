package net.lc;

/**
 * 1289
 * Dynamic Programming
 */
public class MinimumFallingPathSumII {
    public int minFallingPathSum(int[][] array) {
        int len = array.length;
        if (len == 1) return array[0][0];
        int[][] dpRes = new int[array.length][array.length];

        int minVal = Integer.MAX_VALUE;
        int secMinVal = Integer.MAX_VALUE;
        for (int j = 0; j < len; j++) {
            dpRes[0][j] = array[0][j];

            if (dpRes[0][j] < minVal) {
                secMinVal = minVal;
                minVal = dpRes[0][j];
            } else if (dpRes[0][j] < secMinVal) {
                secMinVal = dpRes[0][j];
            }
        }

        int curMinVal = Integer.MAX_VALUE;
        int curSecMinVal = Integer.MAX_VALUE;

        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < len; j++) {

                if (dpRes[i-1][j] != minVal) {
                    dpRes[i][j] = minVal + array[i][j];
                } else {
                    dpRes[i][j] = secMinVal + array[i][j];
                }

                if (dpRes[i][j] < curMinVal) {
                    curSecMinVal = curMinVal;
                    curMinVal = dpRes[i][j];
                } else if (dpRes[i][j] < curSecMinVal) {
                    curSecMinVal = dpRes[i][j];
                }
            }

            minVal = curMinVal;
            secMinVal = curSecMinVal;

            curMinVal = Integer.MAX_VALUE;
            curSecMinVal = Integer.MAX_VALUE;
        }

        return minVal;
    }

    public static void main(String[] args) {
        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(new MinimumFallingPathSumII().minFallingPathSum(array));
    }
}
