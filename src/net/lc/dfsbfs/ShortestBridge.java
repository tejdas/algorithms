package net.lc.dfsbfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 934
 * DFS and BFS
 * Two Islands. Find the shortest distance between the two islands.
 * Pick any land-cell; DFS and convert cells from 1 to 2.
 * (works as visited, and distinguishes the other island).
 * Also, keep track of boundary water cells (adjacent to land).
 *
 * From each boundary cell, start a BFS until we reach a land-cell (1), which means the other island.
 * Return the distance.
 * Return the shortest distance from BFS traversals from all boundary cells.
 */
public class ShortestBridge {

    private static final int[][] neigh = {{-1,0}, {1,0},{0,-1},{0,1}};
    private int[][] grid;
    private int[][] distance;
    private int len;
    /**
     * A borderCell[i][j] has value 1, but one of its adjacent cells  is 0 (water)
     */
    private final List<int[]> borderCells = new ArrayList<>();
    public int shortestBridge(int[][] A) {

        len = A.length;
        this.grid = A;
        distance = new int[len][len];

        int startX = -1, startY = -1;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (A[i][j] == 1) {
                    startX = i;
                    startY = j;
                }
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        dfs(startX, startY);

        return bfs();
    }

    private void dfs(int curx, int cury) {
        if (grid[curx][cury] == 2) return;

        grid[curx][cury] = 2;
        boolean border = false;

        for (int[] n : neigh) {
            int x = curx + n[0];
            int y = cury + n[1];

            if (x >= 0 && x < len && y >= 0 && y < len) {
                if (grid[x][y] == 1) {
                    dfs(x,y);
                } else if (grid[x][y] == 0) {
                    border = true;
                }
            }
        }

        if (border) {
            borderCells.add(new int[]{curx, cury});
        }
    }

    private int bfs() {
        int shortestD = Integer.MAX_VALUE;
        Queue<int[]> queue = new LinkedList<>();

        /**
         * Start BFS from border cells
         */
        for (int[] pos : borderCells) {
            distance[pos[0]][pos[1]] = 0;

            queue.add(new int[] {pos[0], pos[1]});
        }

        while (!queue.isEmpty()) {
            int[] pos = queue.remove();

            for (int[] n : neigh) {
                int x = pos[0] + n[0];
                int y = pos[1] + n[1];

                if (x >= 0 && x < len && y >= 0 && y< len && grid[x][y] != 2) {
                    if (distance[x][y] > distance[pos[0]][pos[1]] + 1) {
                        distance[x][y] = distance[pos[0]][pos[1]] + 1;

                        if (grid[x][y] == 1) {
                            // reached the other island
                            shortestD = Math.min(shortestD, distance[x][y]-1);
                        } else {
                            // in water cell; continue BFS
                            queue.add(new int[] { x, y });
                        }
                    }
                }
            }
        }

        return Math.min(200, shortestD);
    }
}
