package net.lc;

/**
 * 64
 * Dynamic Programming
 */
public class MinimumPathSum {
    public int minPathSum(int[][] grid) {

        if (grid == null) return 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] memoized = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j ==0) memoized[i][j] = grid[i][j];
                else if (i ==0) {
                    memoized[i][j] = memoized[i][j-1] + grid[i][j];
                } else if (j == 0) {
                    memoized[i][j] = memoized[i-1][j] + grid[i][j];
                } else {
                    int leftval = memoized[i - 1][j];
                    int topval = memoized[i][j - 1];

                    memoized[i][j] = Math.min(leftval, topval) + grid[i][j];
                }
            }
        }
        return memoized[rows-1][cols-1];
    }
}
