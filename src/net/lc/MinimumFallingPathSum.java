package net.lc;

/**
 * 931
 * Dynamic Programming
 */
public class MinimumFallingPathSum {
    public int minFallingPathSum(int[][] array) {
        int len = array.length;
        if (len == 0) return 0;
        if (len == 1) return array[0][0];
        int minSum = Integer.MAX_VALUE;
        int[][] dpRes = new int[array.length][array.length];

        for (int j = 0; j < len; j++) {
            dpRes[0][j] = array[0][j];
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < len; j++) {
                int leftTop = (j-1 >= 0)? dpRes[i-1][j-1] : Integer.MAX_VALUE;
                int rightTop = (j+1 < len)? dpRes[i-1][j+1] : Integer.MAX_VALUE;
                int top = dpRes[i-1][j];

                dpRes[i][j] = Math.min(leftTop, Math.min(top, rightTop)) + array[i][j];

                if (i == len-1) {
                    minSum = Math.min(minSum, dpRes[i][j]);
                }
            }
        }

        return minSum;
    }

    public static void main(String[] args) {
        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(new MinimumFallingPathSum().minFallingPathSum(array));
    }
}
