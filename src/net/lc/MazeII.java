package net.lc;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/the-maze-ii/submissions/
 * Dijkstra shortest path
 */
public class MazeII {
    private int[][] maze;

    static class Pair implements Comparable<Pair> {
        int x,y;
        int distance;

        public Pair(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(this.distance, o.distance);
        }
    }

    /**
     * Dijkstra's shortest-path algorithm.
     * If we find a shorter path to an adjacent vertex, then recompute the adjacent vertex and put it in the Queue.
     * @param maze
     * @param start
     * @param dest
     * @return
     */
    public int shortestDistance(int[][] maze, int[] start, int[] dest) {
        int rows = maze.length;
        int cols = maze[0].length;

        int[][] distance = new int[rows][cols];

        boolean[][] visited = new boolean[rows][cols];
        for (boolean[] row: visited) {
            Arrays.fill(row, false);
        }


        for (int[] row: distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distance[start[0]][start[1]] = 0;

        int[][] directions={{0, 1} ,{0, -1}, {-1, 0}, {1, 0}};
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        queue.add(new Pair(start[0], start[1], 0));
        while (!queue.isEmpty()) {
            Pair s = queue.remove();

            if (visited[s.x][s.y]) continue;
            visited[s.x][s.y] = true;

            for (int[] dir: directions) {
                int x = s.x + dir[0];
                int y = s.y + dir[1];
                int count = 0;
                while (x >= 0 && y >= 0 && x < rows && y < cols && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }
                x -= dir[0];
                y -= dir[1];
                if (distance[x][y] > distance[s.x][s.y] + count) {
                    distance[x][y] = distance[s.x][s.y] + count;
                }

                if (!visited[x][y]) {
                    queue.add(new Pair(x, y, distance[x][y]));
                }
            }
        }
        return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
    }
}
