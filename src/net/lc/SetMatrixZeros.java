package net.lc;

/**
 * 73
 */
public class SetMatrixZeros {
    public void setZeroes(int[][] matrix) {
        int numRows = matrix.length;
        if (numRows == 0) return;
        int numCols = matrix[0].length;
        if (numCols == 0) return;

        boolean[] row = new boolean[numRows];
        boolean[] col = new boolean[numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
