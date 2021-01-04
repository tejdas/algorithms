package net.lc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathGridObstacleElimination {
    static int[][] neighs = {{-1,0}, {1,0}, {0,-1}, {0,1}};

    public int shortestPath(int[][] grid, int k) {

        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][][] visited = new boolean[rows][cols][k+1];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                Arrays.fill(visited[i][j], false);

        int[][][] sp = new int[rows][cols][k+1];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                Arrays.fill(sp[i][j], Integer.MAX_VALUE);

        for (int i = 0; i <= k; i++) {
            sp[0][0][i] = 0;
        }
        //sp[0][0][0] = 0;

        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[] {0,0,0});

        while (!queue.isEmpty()) {
            int[] cur = queue.remove();

            int curx = cur[0], cury = cur[1], obstacles = cur[2];
            //System.out.println("x: " + curx + " y: " + cury + " obst: " + obstacles);

            if (visited[curx][cury][obstacles]) continue;

            visited[curx][cury][obstacles] = true;

            for (int[] neig : neighs) {
                int nx = curx + neig[0];
                int ny = cury + neig[1];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                    if (grid[nx][ny] == 1) {
                        // obstacle
                        if (obstacles == k) continue;

                        sp[nx][ny][obstacles+1] = Math.min(sp[nx][ny][obstacles+1], 1 + sp[curx][cury][obstacles]);
                        //System.out.println("with obstacle: nx: " + nx + " ny: " + ny + " sp: " + sp[nx][ny]);

                        if (!visited[nx][ny][obstacles+1]) {
                            queue.add(new int[] {nx, ny, obstacles+1});
                        }
                    } else {

                        sp[nx][ny][obstacles] = Math.min(sp[nx][ny][obstacles], 1 + sp[curx][cury][obstacles]);
                        //System.out.println("without obstacle: nx: " + nx + " ny: " + ny + " sp: " + sp[nx][ny]);

                        if (!visited[nx][ny][obstacles]) {
                            queue.add(new int[] {nx, ny, obstacles});
                        }
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i <= k; i++) {
            sp[rows-1][cols-1][i] = Math.min(min, sp[rows-1][cols-1][i]);
        }
        if (min == Integer.MAX_VALUE) return -1;

        return min;
    }

    public static void main(String[] args) {
        {
            int[][] grid = { { 0, 0, 0 }, { 1, 1, 0 }, { 0, 0, 0 }, { 0, 1, 1 }, { 0, 0, 0 } };
            //System.out.println(new ShortestPathGridObstacleElimination().shortestPath(grid, 1));
        }
        {
            int[][] grid = {{0,1,1},{1,1,1}, {1,0,0} };
           // System.out.println(new ShortestPathGridObstacleElimination().shortestPath(grid, 1));
        }
        {
            int[][] grid = {{0,1,0,0,0,1,0,0},{0,1,0,1,0,1,0,1},{0,0,0,1,0,0,1,0}};
            System.out.println(new ShortestPathGridObstacleElimination().shortestPath(grid, 1));
        }
    }
}
