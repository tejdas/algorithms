package net.lc.bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 1091
 * BFS
 * Greedy shortest path
 * Note: for BFS, most of the time, no need to use visited flag.
 * At the point of eval/updating the shortest distance of neighbor,
 * add the neighbor to Queue for processing.
 * In this problem, it improved the performance from 12% to 73%.
 */
public class ShortestPathBinaryMatrix {
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null) return 0;
        int n = grid.length;

        if (grid[0][0] == 1) return -1;

        int[][] sp = new int[grid.length][grid[0].length];
        for (int[] a : sp) {
            Arrays.fill(a, Integer.MAX_VALUE);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0,0});
        sp[0][0] = 1;

        while (!queue.isEmpty()) {
            int[] cur = queue.remove();
            int x = cur[0];
            int y = cur[1];

            for (int i = x-1; i <= x+1; i++) {
                for (int j = y-1; j <= y+1; j++) {
                    if (i == x && j == y) continue;

                    if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 0) {
                        if (sp[i][j] > sp[x][y] + 1) {
                            sp[i][j] = sp[x][y] + 1;

                            if (i != n-1 || j != n-1) {
                                queue.add(new int[] { i, j });
                            }
                        }
                    }
                }
            }
        }

        if (sp[n-1][n-1] != Integer.MAX_VALUE) {
            return sp[n-1][n-1];
        } else {
            return -1;
        }
    }
}
