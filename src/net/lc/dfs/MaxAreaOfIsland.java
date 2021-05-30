package net.lc.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 695
 * DFS
 */
public class MaxAreaOfIsland {
    private boolean[][] visited = null;
    int numRows = 0;
    int numCols = 0;
    private int[][] notvisitedCells = null;

    private int maxArea = 0;
    private int currentArea = 0;
    private int countNotVisited = 0;

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        if (grid[0] == null) {
            return 0;
        }
        numRows = grid.length;
        numCols = grid[0].length;
        maxArea = 0;
        currentArea = 0;
        countNotVisited = 0;

        visited = new boolean[numRows][numCols];
        notvisitedCells = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                visited[i][j] = false;
                notvisitedCells[i][j] = -1;

                if (grid[i][j] == 1) {
                    notvisitedCells[i][j] = 0;
                    countNotVisited++;
                }
            }
        }

        while (countNotVisited > 0) {
            Pair p = getUnvisitedCell();
            currentArea = 0;
            dfs(grid, p.x, p.y);
            maxArea = Math.max(maxArea, currentArea);
        }

        return maxArea;
    }

    private void dfs(int[][] grid, int x, int y) {
        visited[x][y] = true;
        currentArea++;
        notvisitedCells[x][y] = 1;
        countNotVisited--;
        List<Pair> neighbors = getNeighbors(grid, x, y);
        for (Pair p : neighbors) {
            if (!visited[p.x][p.y]) {
                dfs(grid, p.x, p.y);
            }
        }
    }

    private List<Pair> getNeighbors(int[][] grid, int x, int y) {
        List<Pair> neighbors = new ArrayList<>();

        if (x-1 >= 0 && grid[x-1][y] == 1) {
            neighbors.add(new Pair(x-1, y));
        }

        if (x+1 < numRows && grid[x+1][y] == 1) {
            neighbors.add(new Pair(x+1, y));
        }

        if (y-1 >= 0 && grid[x][y-1] == 1) {
            neighbors.add(new Pair(x, y-1));
        }

        if (y+1 < numCols && grid[x][y+1] == 1) {
            neighbors.add(new Pair(x, y+1));
        }
        return neighbors;
    }

    private Pair getUnvisitedCell() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (notvisitedCells[i][j] == 0) {
                    return new Pair(i, j);
                }
            }
        }
        return null;
    }
}
