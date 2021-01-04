package net.lc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/as-far-from-land-as-possible/
 * BFS
 * Graph
 */
public class AsFarFromLandAsPosssible {
    private static final int[][] adjList = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    public int maxDistance(int[][] grid) {
        if (grid == null) return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        int[][] distance = new int[rows][cols];

        boolean foundWater = false;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    foundWater = true;
                    distance[i][j] = Integer.MAX_VALUE;
                } else {
                    distance[i][j] = 0;
                    queue.add(new int[] {i, j});
                }
            }
        }

        if (!foundWater || queue.isEmpty()) return -1;

        int maxDistance = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int[] cur = queue.remove();

            int curx = cur[0];
            int cury = cur[1];

            for (int[] adj : adjList) {
                int adjx = curx + adj[0];
                int adjy = cury + adj[1];

                if (adjx >= 0 && adjx < rows && adjy >= 0 && adjy < cols && grid[adjx][adjy] == 0) {
                    if (distance[adjx][adjy] > 1 + distance[curx][cury]) {
                        distance[adjx][adjy] = 1 + distance[curx][cury];

                        maxDistance = Math.max(maxDistance, distance[adjx][adjy]);

                        queue.add(new int[] {adjx, adjy});
                    }
                }
            }
        }
        return maxDistance;
    }
}
