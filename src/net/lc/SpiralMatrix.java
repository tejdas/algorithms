package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/spiral-matrix/submissions/
 * Array
 */
public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {

        int rows = matrix.length;
        if (rows == 0) return null;
        int cols = matrix[0].length;

        List<Integer> list = new ArrayList<>();

        int iterCount = rows/2 + 1;

        for (int iter = 0; iter < iterCount; iter++) {

            if (iter > cols-iter-1) break;
            for (int j = iter; j < cols-iter; j++) {
                list.add(matrix[iter][j]);
            }


            if (iter+1 > rows-iter-1) break;
            for (int i = iter+1; i <= rows-iter-1; i++) {
                list.add(matrix[i][cols-iter-1]);
            }


            if (iter > cols-iter-2) break;
            if (iter == rows-iter-1) break;
            for (int j = cols-iter-2; j >= iter; j--) {
                list.add(matrix[rows-iter-1][j]);
            }


            if (iter+1 > rows-iter-2) break;
            for (int i = rows-iter-2; i >= iter+1; i--) {
                list.add(matrix[i][iter]);
            }

        }
        return list;
    }

    public static void main(String[] args) {

        {
            int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

            List<Integer> output = new SpiralMatrix().spiralOrder(matrix);
            for (int i : output)
                System.out.print(i + "  ");
            System.out.println();
        }

        {
            int[][] matrix = new int[][] { { 1, 2, 3, 4 }, { 5,6,7,8 }, { 9,10,11,12 } };

            List<Integer> output = new SpiralMatrix().spiralOrder(matrix);
            for (int i : output)
                System.out.print(i + "  ");
            System.out.println();
        }
    }
}
