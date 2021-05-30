package net.lc.bfs;

import java.util.*;

/**
 * 417
 * BFS
 */
public class PacificAtlanticWaterFlow {
    private static final int[][] neighbor = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
    static class Cell implements Comparable<Cell> {
        int x;
        int y;
        int val;

        public Cell(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo(Cell o) {
            return Integer.compare(this.val, o.val);
        }
    }

    private int[][] matrix;
    private boolean[][] pVisited;
    private boolean[][] aVisited;
    private int rows, cols;

    /**
     * We need to find the list of coordinates, from which water can flow to P & A
     * (along cells equal or lower).
     *
     * We need to start from coast cells (edge) and traverse BFS to higher grounds.
     * Do this BFS traversal twice, once from A coast cells and then from P coast cells.
     * The cells that are visited during both BFS are the answer.
     * @param matrix
     * @return
     */
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix == null) return null;

        this.matrix = matrix;

        rows = matrix.length;
        if (rows == 0) return new ArrayList<>();
        this.cols = matrix[0].length;
        if (cols == 0) return new ArrayList<>();


        pVisited = new boolean[rows][cols];
        aVisited = new boolean[rows][cols];

        PriorityQueue<Cell> ppq = new PriorityQueue<>();
        PriorityQueue<Cell> apq = new PriorityQueue<>();
        for (int i = 0; i < rows; i++) {
            ppq.add(new Cell(i, 0, matrix[i][0]));
            apq.add(new Cell(i, cols-1, matrix[i][cols-1]));
        }

        for (int j = 0; j < cols; j++) {
            ppq.add(new Cell(0, j,matrix[0][j]));
            apq.add(new Cell(rows-1, j, matrix[rows-1][j]));
        }

        List<int[]> alist = new ArrayList<>();
        List<int[]> plist = new ArrayList<>();

        while (!ppq.isEmpty()) {
            Cell c = ppq.remove();
            plist.add(new int[] {c.x, c.y});
        }

        while (!apq.isEmpty()) {
            Cell c = apq.remove();
            alist.add(new int[] {c.x, c.y});
        }

        bfs(plist, pVisited);
        bfs(alist, aVisited);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pVisited[i][j] && aVisited[i][j]) {
                    result.add(Arrays.asList(i,j));
                }
            }
        }

        return result;
    }

    private void bfs(List<int[]> startCells, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        for (int[] startCell : startCells) queue.add(startCell);

        while (!queue.isEmpty()) {
            int[] cell = queue.remove();
            int row = cell[0];
            int col = cell[1];
            if (visited[row][col]) continue;

            visited[row][col] = true;

            for (int[] n : neighbor) {
                int nrow = row + n[0];
                int ncol = col + n[1];

                if (nrow >= 0 && nrow < rows && ncol >= 0 && ncol < cols) {
                    if (matrix[nrow][ncol] >= matrix[row][col]) {
                        if (!visited[nrow][ncol]) {
                            queue.add(new int[] { nrow, ncol });
                        }
                    }
                }
            }
        }
    }
}
