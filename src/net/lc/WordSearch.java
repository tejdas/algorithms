package net.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * DFS
 * Stack
 */
public class WordSearch {
    static class Cell {
        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Cell other = (Cell) obj;
            return (column==other.column && row==other.row);
        }

        public Cell(final int row, final int column) {
            this.row = row;
            this.column = column;
        }

        int row;

        int column;
    }

    static char[][] matrix = null;

    static int rows;

    static int columns;

    static void displayMatrix() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++)
                System.out.print(matrix[r][c] + "  ");
            System.out.println();
        }
    }

    private static List<Cell> getNeighbors(final Stack<Cell> path, final Cell current) {

        final List<Cell> neighbors = new ArrayList<>();

        int x = current.row;
        int y = current.column;

        if (x-1 >= 0) {
            Cell c = new Cell(x-1, y);
            if (!path.contains(c)) {
                neighbors.add(c);
            }
        }

        if (x+1 < rows) {
            Cell c = new Cell(x+1, y);
            if (!path.contains(c)) {
                neighbors.add(c);
            }
        }

        if (y-1 >= 0) {
            Cell c = new Cell(x, y-1);
            if (!path.contains(c)) {
                neighbors.add(c);
            }
        }

        if (y+1 < columns) {
            Cell c = new Cell(x, y+1);
            if (!path.contains(c)) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }

    static boolean match(final char[] array, final int pos, final Stack<Cell> path, final Cell current) {
        final List<Cell> neighbors = getNeighbors(path, current);
        for (final Cell neighbor : neighbors) {
            if (matrix[neighbor.row][neighbor.column] == array[pos]) {
                path.push(neighbor);
                if (pos == array.length - 1)
                    return true;
                if (match(array, pos + 1, path, neighbor))
                    return true;
                path.pop();
            }
        }
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
        final List<Cell> startCells = new ArrayList<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < columns; c++)
                if (matrix[r][c] == array[0])
                    startCells.add(new Cell(r, c));

        if (array.length == 1) {
            return !startCells.isEmpty();
        }

        for (final Cell startCell : startCells) {
            final Stack<Cell> paths = new Stack<>();
            paths.add(startCell);
            if (match(array, 1, paths, startCell)) {
                //for (final Cell path : paths)
                //  System.out.println(path.row + "  " + path.column);
                return true;
            }
        }

        return false;
    }
}
