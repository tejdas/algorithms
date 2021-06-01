package net.lc.dfs;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/path-with-minimum-effort/submissions/
 * DFS
 * Binary-Search on the possible values
 * 1631
 */
public class PathWithMinimumEffort {
    int[][] array;
    int rows;
    int cols;
    boolean[][] visited;
    int maxValue = Integer.MIN_VALUE;

    static int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    int maxDiff = Integer.MIN_VALUE;
    int minDiff = Integer.MAX_VALUE;
    public int minimumEffortPath(int[][] heights) {
        this.array = heights;
        rows = array.length;
        cols = array[0].length;

        if (rows == 1 && cols == 1) return 0;

        visited = new boolean[rows][cols];
        findMinMax(new int[]{0,0});

        /**
         * Binary search on the min reach value
         */
        int left = minDiff;
        int right = maxDiff;

        int mid = 0;

        while (left <= right) {
            mid = (left+right)/2;
            boolean flag = checkReach(mid);

            if (flag) {
                right = mid-1;
            } else {
                left = mid+1;
            }
        }

        return checkReach(mid)? mid : mid+1;
    }

    public void resetVisited() {
        for (boolean[] ar : visited)
            Arrays.fill(ar, false);
    }

    /**
     * DFS traverse the entire matrix, and find min-diff between two adj cells max-diff between two adjacent cells.
     * @param curNode
     */
    public void findMinMax(int[] curNode) {
        int row = curNode[0];
        int col = curNode[1];

        visited[row][col] = true;

        for (int[] adj : dir) {
            int adjx = row+adj[0];
            int adjy = col+adj[1];

            if (adjx >= 0 && adjx < rows && adjy >= 0 && adjy < cols) {
                int diff = Math.abs(array[row][col] - array[adjx][adjy]);

                maxDiff = Math.max(maxDiff, diff);
                minDiff = Math.min(minDiff, diff);
                if (!visited[adjx][adjy]) {
                    findMinMax(new int[] {adjx, adjy});
                }
            }
        }
    }

    private boolean checkReach(int limit) {
        resetVisited();
        return canReach(new int[]{0,0}, limit);
    }

    /**
     * DFS starting from {0,0} to see if we can reach last cell, while staying at/below limit.
     * @param curNode
     * @param limit
     * @return
     */
    private boolean canReach(int[] curNode, int limit) {
        int row = curNode[0];
        int col = curNode[1];

        if (row == rows-1 && col == cols-1) return true;
        visited[row][col] = true;

        for (int[] adj : dir) {
            int adjx = row+adj[0];
            int adjy = col+adj[1];

            if (adjx >= 0 && adjx < rows && adjy >= 0 && adjy < cols) {
                int diff = Math.abs(array[row][col] - array[adjx][adjy]);

                if (diff > limit) continue;

                if (!visited[adjx][adjy]) {
                    if (canReach(new int[] {adjx, adjy}, limit)) return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        {
            int[][] heights = { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } };
            System.out.println(new PathWithMinimumEffort().minimumEffortPath(heights));
        }

        {
            int[][] heights = { { 1, 2, 3 }, { 3, 8, 4 }, { 5, 3, 5 } };
            System.out.println(new PathWithMinimumEffort().minimumEffortPath(heights));
        }

        {
            int[][] heights = {{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}};
            System.out.println(new PathWithMinimumEffort().minimumEffortPath(heights));
        }

        {
            int[][] heights = {{7,9}, {3,6}, {6,9}, {3,6}};
            System.out.println(new PathWithMinimumEffort().minimumEffortPath(heights));
        }
    }
}
