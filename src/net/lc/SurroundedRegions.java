package net.lc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/surrounded-regions/
 * BFS
 */
public class SurroundedRegions {
    private static final int[][] neigh = {{-1,0}, {1,0},{0,-1},{0,1}};
    public void solve(char[][] board) {
        int rows = board.length;
        if (rows == 0) return;
        int cols = board[0].length;

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') queue.add(new int[] {i, 0});
            if (board[i][cols-1] == 'O') queue.add(new int[] {i, cols-1});
        }

        for (int j = 1; j < cols-1; j++) {
            if (board[0][j] == 'O') queue.add(new int[] {0, j});
            if (board[rows-1][j] == 'O') queue.add(new int[] {rows-1, j});
        }

        while (!queue.isEmpty()) {
            int[] cell = queue.remove();
            int curx = cell[0];
            int cury = cell[1];

            if (board[curx][cury] == 'P') continue;

            board[curx][cury] = 'P';

            for (int[] n : neigh) {
                int x = curx + n[0];
                int y = cury + n[1];

                if (x >= 0 && x < rows && y >= 0 && y< cols && board[x][y] == 'O') {
                    queue.add(new int[] {x,y});
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'P') board[i][j] = 'O';
            }
        }
    }

    public static void main(String[] args) {
        char[][] board = {{'X', 'X', 'X', 'X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        new SurroundedRegions().solve(board);
        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
