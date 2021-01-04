package xxx.yyy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MatrixRotator {
    static int[][] buildMatrix(final int size) {
        final Random r = new Random();
        final Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < size*size) {
            numbers.add(r.nextInt(90) + 10);
        }

        final Integer[] numbersArray = numbers.toArray(new Integer[numbers.size()]);

        final int[][] array = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = numbersArray[i*size + j];
            }
        }
        return array;
    }

    static int[][] buildMatrix(final int size, final int maxValue) {
        final Random r = new Random();
        final Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < size*size) {
            numbers.add(r.nextInt(maxValue));
        }

        final Integer[] numbersArray = numbers.toArray(new Integer[numbers.size()]);

        final int[][] array = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = numbersArray[i*size + j];
            }
        }
        return array;
    }

    static void printMatrixDiagonal(final int[][] array, final int size) {
    	for (int col = 0; col < size; col++) {
    		int row = 0;
    		for (int foo = col; foo >= 0; foo--) {
    			System.out.print(array[row++][foo] + " ");
    		}
    		System.out.println();
    	}
    	for (int row = 1; row < size; row++) {
    		for (int col = size-1, foo = row; foo<size; col--,foo++) {
    			System.out.print(array[foo][col] + " ");
    		}
    		System.out.println();
    	}
    }

    public static void printMatrix(final int[][] array, final int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    static void printMatrix(final int[][] array, final int sizex, final int sizey) {
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    static void rotateTwoRows(final int[] a, final int[] b) {
        final int size = a.length;
        final int range = (a == b)? size/2 : size;
        for (int i = 0; i < range; i++) {
            final int temp = a[i];
            a[i] = b[size-i-1];
            b[size-i-1] = temp;
        }
    }

    static void rotateMatrix(final int[][] matrix, final int size) {
        final int range = (size%2 == 0)? size/2 : size/2 + 1;
        for (int i = 0; i < range; i++) {
            final int[] a = matrix[i];
            final int[] b = matrix[size-i-1];
            rotateTwoRows(a, b);
        }
    }

    static void rotateMatrix90(final int[][] matrix, final int begin, final int end) {
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
        rotateMatrix90(matrix, begin+1, end-1);
    }

    static void rotateMatrix180(final int[][] matrix, final int begin, final int end) {
        final int range = end - begin;
        if (range <= 0) {
            return;
        }
        for (int i = 0; i < range; i++) {
            int temp = matrix[begin][begin+i];
            matrix[begin][begin+i] = matrix[end][end-i];
            matrix[end][end-i] = temp;

            temp = matrix[begin+i][end];
            matrix[begin+i][end] = matrix[end-i][begin];
            matrix[end-i][begin] = temp;
        }
        rotateMatrix180(matrix, begin+1, end-1);
    }

    public static void minCostPath(final int[][] matrix) {
        final int[][] minCosts = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i==0 && j==0) {
                    minCosts[i][j] = matrix[i][j];
                    continue;
                }

                int minCostDiag = Integer.MAX_VALUE;
                int minCostFromLeft = Integer.MAX_VALUE;
                int minCostFromTop = Integer.MAX_VALUE;
                if (i > 0 && j > 0) {
                    minCostDiag = minCosts[i-1][j-1];
                }

                if (i > 0) {
                    minCostFromTop = minCosts[i-1][j];
                }

                if (j > 0) {
                    minCostFromLeft = minCosts[i][j-1];
                }

                System.out.println("For: " + i + " and " + j + " diag: " + minCostDiag + " top: " + minCostFromTop + " left: " + minCostFromLeft);
                minCosts[i][j] = matrix[i][j] + Math.min(minCostDiag, Math.min(minCostFromLeft, minCostFromTop));
            }
        }

        printMatrix(minCosts, minCosts.length);

        System.out.println("min cost: " + minCosts[matrix.length-1][matrix.length-1]);
    }

    public static void main(final String[] args) {
        final int matrixSize = 3;
        final int[][] matrix = buildMatrix(matrixSize, 15);
        printMatrix(matrix, matrixSize);

        minCostPath(matrix);
    }

    public static void main1(final String[] args) {
        final int matrixSize = 8;
        final int[][] matrix = buildMatrix(matrixSize);
        printMatrix(matrix, matrixSize);
        //rotateMatrix(matrix, matrixSize);
        //printMatrix(matrix, matrixSize);
        rotateMatrix90(matrix, 0, matrixSize-1);
        printMatrix(matrix, matrixSize);
        rotateMatrix180(matrix, 0, matrixSize-1);
        printMatrix(matrix, matrixSize);
    }

    public static void main2(final String[] args) {
        final int matrixSize = 8;
        final int[][] matrix = buildMatrix(matrixSize);
        printMatrix(matrix, matrixSize);
        printMatrixDiagonal(matrix, matrixSize);
    }
}
