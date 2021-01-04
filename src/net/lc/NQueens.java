package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 51
 * Recursion
 * Backtracking
 */
public class NQueens {
    private static List<List<String>> chessBoards = new ArrayList<>();

    private static void placeQueen(final int queenIndex, final int[] array) {
        if (queenIndex == array.length) {
            /*
             * All queens have been placed. Print the output
             */
            printChessBoard(array);
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

    public List<List<String>> solveNQueens(int n) {
        chessBoards.clear();

        final int[] array = new int[n];
        Arrays.fill(array, -1);
        placeQueen(0, array);

        return chessBoards;
    }

    private static void printChessBoard(int[] array) {
        final char[][] chessBoard = new char[array.length][array.length];
        for (final char[] cbRow : chessBoard) {
            Arrays.fill(cbRow, '.');
        }

        for (int i = 0; i < array.length; i++) {
            chessBoard[i][array[i]] = 'Q';
        }

        List<String> sol = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            sol.add(new String(chessBoard[i]));
        }

        chessBoards.add(sol);
    }
}
