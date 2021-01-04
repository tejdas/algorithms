package algo.recurse;

import java.util.Arrays;

/**
 * Place n-Queens on a chess-board so that, no two queens are in the same row,
 * same column or in the same diagonal.
 *
 * Recursively finds all Queen placements, using greedy (backtracking)
 * algorithm.
 */
class QueenPlacer {

    private static int count = 0;

    private static void placeQueen(final int queenIndex, final int[] array) {
        if (queenIndex == array.length) {
            /*
             * All queens have been placed. Print the output
             */
            printChessBoard(array);
            count++;
            return;
        }

        for (int columnIndex = 0; columnIndex < array.length; columnIndex++) {
            if (array[columnIndex] == -1) {
                array[columnIndex] = queenIndex;

                boolean queensCanAttack = false;
                for (int rowIndex = 0; rowIndex < array.length; rowIndex++) {
                    if (canQueensAttackEachOther(array, rowIndex, columnIndex)) {
                        queensCanAttack = true;
                        break;
                    }
                }

                if (!queensCanAttack) {
                    final int nextQueen = queenIndex + 1;
                    placeQueen(nextQueen, array);
                }
                array[columnIndex] = -1;
            }
        }
    }

    private static boolean canQueensAttackEachOther(int[] array, int rowIndex, int columnIndex) {
        return ((rowIndex != columnIndex) && (array[rowIndex] != -1)
                && (Math.abs(array[rowIndex] - array[columnIndex]) == Math.abs(rowIndex - columnIndex)));
    }

    public static void main(final String[] args) {
        final int n = 8; // n-Queen problem
        final int[] array = new int[n];
        Arrays.fill(array, -1);
        placeQueen(0, array);
        System.out.println("Total number of different ways in which Queens can be placed : " + count);
    }

    private static void printChessBoard(int[] array) {
        System.out.println(Arrays.toString(array));
        final char[][] chessBoard = new char[array.length][array.length];
        for (final char[] cbRow : chessBoard) {
            Arrays.fill(cbRow, '0');
        }

        for (int i = 0; i < array.length; i++) {
            chessBoard[i][array[i]] = 'X';
        }

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                System.out.print(chessBoard[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}
