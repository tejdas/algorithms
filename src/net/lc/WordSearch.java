package net.lc;

import java.util.*;

/**
 * 79
 * DFS
 * Stack
 */
public class WordSearch {
    static char[][] matrix = null;

    static int rows;

    static int columns;

    static final int[][] neighs = {{0,1},{0,-1},{1,0},{-1,0}};

    private boolean dfs(char[] array, int pos, final Set<Integer> path, final int[] current) {
        System.out.println("DFS: pos " + pos + "  current: " + Arrays.toString(current) + "  path len: " + path.size());
        if (pos == array.length-1) return true;

        int curx = current[0];
        int cury = current[1];

        int curIndex = toIndex(curx, cury);
        path.add(curIndex);

        for (int[] neigh : neighs) {
            int neighx = curx + neigh[0];
            int neighy = cury + neigh[1];

            if (neighx >= 0 && neighx < rows && neighy >= 0 && neighy < columns) {
                if (!path.contains(toIndex(neighx, neighy)) && matrix[neighx][neighy] == array[pos+1]) {
                    if (dfs(array, pos+1, path, new int[] {neighx, neighy})) return true;
                }
            }
        }
        path.remove(curIndex);
        return false;
    }

    public boolean exist(char[][] board, String word) {
        matrix = board;

        if (matrix == null || matrix.length == 0) {
            return false;
        }

        if (matrix[0] == null) {
            return false;
        }

        rows = matrix.length;
        columns = matrix[0].length;

        final char[] array = word.toCharArray();
        final List<int[]> startCells = new ArrayList<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < columns; c++)
                if (matrix[r][c] == array[0])
                    startCells.add(new int[] {r,c});

        if (array.length == 1) {
            return !startCells.isEmpty();
        }

        for (int[] startCell : startCells) {
            final Set<Integer> paths = new HashSet<>();
            if (dfs(array, 0, paths, startCell)) {
                return true;
            }
        }

        return false;
    }

    static int toIndex(int row, int col) {
        return row * columns + col;
    }

    public static void main(String[] args) {
        char[][] matrix = new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};

        System.out.println(new WordSearch().exist(matrix, "ABCCED"));
    }
}
