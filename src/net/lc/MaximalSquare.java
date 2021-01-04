package net.lc;

/**
 * 221
 * Dynamic Programming
 */
public class MaximalSquare {
    private int maxSquare = 0;
    private int[][] result;

    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) return 0;
        if (matrix[0].length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = -1;
            }
        }

        maxSquare = 0;
        computeMaxSquare(rows-1, cols-1, matrix);
        return maxSquare*maxSquare;
    }

    private int computeMaxSquare(int row, int col, char[][] matrix) {
        if (row < 0 || col < 0) return 0;

        if (result[row][col] != -1) {
            return result[row][col];
        }

        /**
         * intersection of maximal square on top, left and upper-left corner
         * Find all the three values, and get the minimum.
         */
        int leftVal = computeMaxSquare(row, col-1, matrix);
        int topVal = computeMaxSquare(row-1, col, matrix);
        int cornerVal = computeMaxSquare(row-1, col-1, matrix);

        if (matrix[row][col] == '1') {
            int val = Math.min(leftVal, Math.min(topVal, cornerVal)) + 1;
            if (val > maxSquare) {
                maxSquare = val;
            }

            result[row][col] = val;
        } else {
            result[row][col] = 0;
        }
        return result[row][col];
    }
}
