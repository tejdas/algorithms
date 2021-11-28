package net.lc.dfs;

/**
 * 200
 * DFS
 */
public class NumberOfIslands {
    private static final int[][] neighs = {{-1, 0}, {1,0}, {0,-1}, {0,1}};
    private boolean[][] visited = null;
    int numRows = 0;
    int numCols = 0;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        if (grid[0] == null) {
            return 0;
        }
        numRows = grid.length;
        numCols = grid[0].length;

        int islandCounter = 0;

        visited = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    islandCounter++;
                    dfs(grid, i,j);
                }
            }
        }

        return islandCounter;
    }

    private void dfs(char[][] grid, int x, int y) {
        if (visited[x][y]) return;
        visited[x][y] = true;

        for (int[] neigh : neighs) {
            int nx = x + neigh[0];
            int ny = y + neigh[1];

            if (nx >= 0 && nx < numRows && ny >= 0 && ny < numCols && grid[nx][ny] == '1' && !visited[nx][ny]) {
                dfs(grid, nx, ny);
            }
        }
    }
}
