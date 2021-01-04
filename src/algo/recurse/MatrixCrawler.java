package algo.recurse;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MatrixCrawler {
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

    static List<Cell> buildNeighbors(final Stack<Cell> path, final Cell current) {
        final List<Cell> neighbors = new ArrayList<>();
        for (int r = current.row - 1; r <= current.row + 1; r++)
            for (int c = current.column - 1; c <= current.column + 1; c++) {
                if (r < 0 || r >= rows)
                    continue;
                if (c < 0 || c >= columns)
                    continue;
                final Cell cell = new Cell(r, c);
                if (cell.equals(current))
                	continue;
                if (path.contains(cell))
                    continue;
                neighbors.add(cell);
            }
        return neighbors;
    }

    static boolean match(final char[] array, final int pos, final Stack<Cell> path, final Cell current) {
        final List<Cell> neighbors = buildNeighbors(path, current);
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

    static boolean match(final String str) {
        final char[] array = str.toCharArray();
        final List<Cell> startCells = new ArrayList<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < columns; c++)
                if (matrix[r][c] == array[0])
                    startCells.add(new Cell(r, c));

        for (final Cell startCell : startCells) {
            final Stack<Cell> paths = new Stack<>();
            paths.add(startCell);
            if (match(array, 1, paths, startCell)) {
                for (final Cell path : paths)
                    System.out.println(path.row + "  " + path.column);
                return true;
            }
        }

        return false;
    }

    public static void main1(final String[] args) {
        MatrixCrawler.rows = 4;
        MatrixCrawler.columns = 4;
        MatrixCrawler.matrix = new char[4][];
        MatrixCrawler.matrix[0] = new char[] { 'S', 'M', 'E', 'F' };
        MatrixCrawler.matrix[1] = new char[] { 'R', 'A', 'T', 'D' };
        MatrixCrawler.matrix[2] = new char[] { 'L', 'O', 'N', 'I' };
        MatrixCrawler.matrix[3] = new char[] { 'K', 'A', 'F', 'B' };

        displayMatrix();

        final boolean found = match("RALAD");
        System.out.println(found);
    }

    public static void main(final String[] args) {
        MatrixCrawler.rows = 3;
        MatrixCrawler.columns = 3;
        MatrixCrawler.matrix = new char[3][];
        MatrixCrawler.matrix[0] = new char[] { 'G', 'I', 'Z'};
        MatrixCrawler.matrix[1] = new char[] { 'U', 'E', 'K'};
        MatrixCrawler.matrix[2] = new char[] { 'Q', 'S', 'E'};

        displayMatrix();

        final boolean found = match("QUIZ");
        System.out.println(found);
    }
}
