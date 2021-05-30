package net.lc.dfs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 694
 * DFS
 */
public class NumberOfDistinctIslands {
    static int[][] adjList = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    private boolean[][] visited = null;
    int numRows = 0;
    int numCols = 0;
    private Set<Integer> notvisitedCells = new HashSet<>();
    private List[][] neighborMap;

    private int convert(int x, int y) {
        return x * numCols + y;
    }

    public int numDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        if (grid[0] == null) {
            return 0;
        }
        numRows = grid.length;
        numCols = grid[0].length;
        neighborMap = new List[numRows][numCols];

        visited = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                visited[i][j] = false;

                if (grid[i][j] == 1) {
                    notvisitedCells.add(convert(i, j));
                }
            }
        }

        Set<String> islandSet = new HashSet<>();
        while (!notvisitedCells.isEmpty()) {
            int p = notvisitedCells.iterator().next();
            Set<Integer> islandMembers = new HashSet<>();
            dfs(grid, p, islandMembers);

            islandSet.add(convertIslandToString(islandMembers));
        }

        return islandSet.size();
    }

    private void dfs(int[][] grid, int val, Set<Integer> islandMembers) {

        int x = val / numCols;
        int y = val % numCols;

        islandMembers.add(val);
        visited[x][y] = true;
        notvisitedCells.remove(val);

        for (int[] adj : adjList) {
            int neigx = x + adj[0];
            int neigy = y + adj[1];

            if (neigx >= 0 && neigx < numRows && neigy >= 0 && neigy < numCols && grid[neigx][neigy] == 1) {
                if (!visited[neigx][neigy]) {
                    dfs(grid, convert(neigx, neigy), islandMembers);
                }
            }

        }
    }

    private String convertIslandToString(Set<Integer> members) {
        int rowMin = Integer.MAX_VALUE;
        int colMin = Integer.MAX_VALUE;

        int rowMax = Integer.MIN_VALUE;
        int colMax = Integer.MIN_VALUE;

        for (int member : members) {
            int x = member / numCols;
            int y = member % numCols;

            rowMin = Math.min(rowMin, x);
            rowMax = Math.max(rowMax, x);

            colMin = Math.min(colMin, y);
            colMax = Math.max(colMax, y);
        }

        int[][] matrix = new int[rowMax-rowMin+1][colMax-colMin+1];
        for (int member : members) {
            int x = member / numCols;
            int y = member % numCols;

            x -= rowMin;
            y -= colMin;

            //System.out.println("x: " + x + "  y: " + y);

            matrix[x][y] = 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    String r1 = (i < 10)? '0' + String.valueOf(i) : String.valueOf(i);
                    String c1 = (j < 10)? '0' + String.valueOf(j) : String.valueOf(j);
                    sb.append(r1);
                    sb.append(c1);
                } else {
                    sb.append("9999");
                }
            }
        }
        return sb.toString();
    }
}
