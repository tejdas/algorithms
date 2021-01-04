package net.lc;

/**
 * 240
 * Start from bottom-left of matrix (rows-1, 0)
 * If target is bigger than current cell, move right.
 * If target is smaller than current cell, move up.
 */
public class Search2DMatrixII {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) return false;
        if (matrix[0].length == 0) return false;
        int maxCols = matrix[0].length;
        int row = matrix.length-1;
        int col = 0;

        while (true) {
            if (target > matrix[row][col]) {
                col++;
                if (col == maxCols) {
                    break;
                }
            } else if (target < matrix[row][col]) {
                row--;
                if (row < 0) {
                    break;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
