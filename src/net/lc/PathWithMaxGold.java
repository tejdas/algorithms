package net.lc;

import java.util.*;

public class PathWithMaxGold {
    static int[][] neighs = {{-1,0},{1,0},{0,-1},{0,1}};
    int rows;
    int cols;
    int[][] grid;
    private List<int[]> startNodes = new ArrayList<>();
    //private final Set<Integer> visited = new HashSet<>();

    private boolean[][] visited;
    int maxSum = Integer.MIN_VALUE;
    public int getMaximumGold(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        if (rows == 0) return 0;
        cols = grid[0].length;
        if (cols == 0) return 0;

        visited = new boolean[rows][cols];
        //determineStartNodes();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0)
                    continue;

                maxSum = Math.max(maxSum, grid[i][j]);
                //visited.clear();
                dfs(i,j, 0);

            }
        }
/*
        for (int[] stNode : startNodes) {
            visited.clear();
            dfs(null, stNode, 0);
        }
*/
        return maxSum;
    }

    private void determineStartNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) continue;

                int count = 0;
                for (int[] neig : neighs) {
                    int nx = neig[0] + i;
                    int ny = neig[1] + j;

                    if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && grid[nx][ny] > 0) {
                        count++;
                    }
                }

                if (count == 1) {
                    startNodes.add(new int[] {i, j});
                }
            }
        }
    }

    private void dfs(int curx, int cury, int pathSum) {
        //int nodeVal = cur[0] * cols + cur[1];

        //if (visited.contains(nodeVal)) return;
        //visited.add(nodeVal);

        if (visited[curx][cury]) return;

        visited[curx][cury] = true;
        pathSum += grid[curx][cury];

        boolean isTerminal = true;
        for (int[] neig : neighs) {
            int nx = neig[0] + curx;
            int ny = neig[1] + cury;

            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && grid[nx][ny] > 0) {
                //int neigNodeVal = nx * cols+ny;

                if (!visited[nx][ny]) {
                //if (!visited.contains(neigNodeVal)) {
                    isTerminal = false;
                    dfs(nx, ny, pathSum);
                }
            }
        }

        if (isTerminal) {
            maxSum = Math.max(maxSum, pathSum);
        }
        //visited.remove(nodeVal);
        visited[curx][cury] = false;
    }

    public static void main(String[] args) {
        /*
        {
            int[][] grid = { { 0, 6, 0 }, { 5, 8, 7 }, { 0, 9, 0 } };
            System.out.println(new PathWithMaxGold().getMaximumGold(grid));
        }

        {
            int[][] grid = {{1,0,7},{2,0,6},{3,4,5},{0,3,0},{9,0,20}};
            System.out.println(new PathWithMaxGold().getMaximumGold(grid));
        }
*/
        {
            int[][] grid = {{1,0,7,0,0,0},{2,0,6,0,1,0},{3,5,6,7,4,2},{4,3,1,0,2,0},{3,0,5,0,20,0}};
            System.out.println(new PathWithMaxGold().getMaximumGold(grid));
        }
    }
}
