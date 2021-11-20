package net.lc;

/**
 * 48
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        int begin = 0;
        int end = matrix.length-1;

        while (begin < end) {
            final int range = end - begin;
            if (range <= 0) {
                return;
            }
            for (int i = 0; i < range; i++) {
                final int temp = matrix[end-i][begin];
                matrix[end-i][begin] = matrix[end][end-i];
                matrix[end][end-i] = matrix[begin+i][end];
                matrix[begin+i][end] = matrix[begin][begin+i];
                matrix[begin][begin+i] = temp;
            }
            begin++;
            end--;
        }
    }
}
