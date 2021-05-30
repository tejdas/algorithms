package net.lc.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 317
 * BFS
 */
public class ShortestDistanceFromAllBuildings {
    static final int[][] neigbor = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    private int[][] grid;
    private int rows, cols;

    private int[][] totalDistance;

    public int shortestDistance(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = grid[0].length;
        totalDistance = new int[rows][cols];

        /**
         * From each building, do a BFS and find shortest path to all empty lands.
         * Keep adding the shortest path for an empty land from all BFS traversals (from the buildings).
         */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    bfs(i, j);
                }
            }
        }

        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (totalDistance[i][j] != Integer.MAX_VALUE && totalDistance[i][j] > 0 && (totalDistance[i][j] < minDistance)) {
                    minDistance = totalDistance[i][j];
                }
            }
        }
        return minDistance==Integer.MAX_VALUE? -1 : minDistance;
    }

    private void bfs(int x, int y) {
        int[][] distance = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i==x && j==y)
                    distance[i][j] = 0;
                else
                    distance[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {x,y});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curx = cur[0];
            int cury = cur[1];

            for (int[] neig : neigbor) {
                int adjx = curx + neig[0];
                int adjy = cury + neig[1];

                if (adjx >= 0 && adjx < rows && adjy >= 0 && adjy < cols && grid[adjx][adjy] == 0) {
                    if (distance[adjx][adjy] > 1 + distance[curx][cury]) {
                        distance[adjx][adjy] = 1 + distance[curx][cury];
                        queue.add(new int[] {adjx, adjy});
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    if (distance[i][j] != Integer.MAX_VALUE) {
                        if (totalDistance[i][j] != Integer.MAX_VALUE) {
                            totalDistance[i][j] += distance[i][j];
                        }
                    } else {
                        totalDistance[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }
    }
}
