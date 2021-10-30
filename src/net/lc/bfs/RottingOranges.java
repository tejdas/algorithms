package net.lc.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/rotting-oranges/submissions/
 * BFS
 */
public class RottingOranges {
    public int orangesRotting(int[][] grid) {
        return compute(grid);
    }

    private static final int[][] neigh = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    private int compute2(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();

        int freshOrangeCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2)
                    queue.add(new int[] { i, j });
                else if (grid[i][j] == 1)
                    freshOrangeCount++;
            }
        }

        Queue<int[]> secondaryQueue = new LinkedList<>();
        int totalMins = 0;

        while (true) {
            while (!queue.isEmpty()) {
                int[] pos = queue.remove();

                for (int[] n : neigh) {
                    int x = pos[0] + n[0];
                    int y = pos[1] + n[1];

                    if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        freshOrangeCount--;
                        secondaryQueue.add(new int[] { x, y });
                    }
                }
            }

            if (secondaryQueue.isEmpty())
                break;
            totalMins++;
            Queue<int[]> temp = secondaryQueue;
            secondaryQueue = queue;
            queue = temp;
        }

        if (freshOrangeCount == 0) {
            return totalMins;
        } else {
            return -1;
        }
    }

    private int compute(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();

        int[][] dist = new int[rows][cols];

        int freshOrangeCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    dist[i][j] = 0;
                    queue.add(new int[] { i, j });
                } else if (grid[i][j] == 1) {
                    freshOrangeCount++;
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        int totalMins = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int[] pos = queue.remove();

            for (int[] n : neigh) {
                int x = pos[0] + n[0];
                int y = pos[1] + n[1];

                if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
                    grid[x][y] = 2;
                    freshOrangeCount--;

                    if (dist[x][y] > 1 + dist[pos[0]][pos[1]]) {
                        dist[x][y] = 1 + dist[pos[0]][pos[1]];
                        totalMins = Math.max(totalMins, dist[x][y]);

                        queue.add(new int[] { x, y });
                    }
                }
            }
        }

        if (freshOrangeCount == 0) {
            return totalMins == Integer.MIN_VALUE? 0 : totalMins;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        {
            int[][] input = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
            System.out.println(new RottingOranges().orangesRotting(input));
        }

        {
            int[][] input = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
            System.out.println(new RottingOranges().orangesRotting(input));
        }
    }
}
