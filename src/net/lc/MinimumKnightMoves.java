package net.lc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1197
 * BFS
 */
public class MinimumKnightMoves {
    static final int max = 605;
    static final int[][] neighbors = {{2,1}, {2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};

    private final int[][] board = new int[max][max];
    private final boolean[][] visited = new boolean[max][max];
    public int minKnightMoves(int x, int y) {
        return bfs(x,y);
    }

    private int bfs(int x, int y) {

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                board[i][j] = Integer.MAX_VALUE;
                visited[i][j] = false;
            }
        }
        board[302][302] = 0;

        int targetX = 302+x;
        int targetY = 302+y;

        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{302,302});

        while (!queue.isEmpty()) {
            int[] pos = queue.remove();
            if (visited[pos[0]][pos[1]]) continue;

            visited[pos[0]][pos[1]] = true;

            for (int[] neigh : neighbors) {
                int neighx = pos[0] + neigh[0];
                int neighy = pos[1] + neigh[1];

                if (neighx >= 0 && neighx < max && neighy >= 0 && neighy < max) {
                    if (board[pos[0]][pos[1]] + 1 < board[neighx][neighy]) {
                        board[neighx][neighy] = board[pos[0]][pos[1]] + 1;
                    }

                    if (neighx == targetX && neighy == targetY) {
                        return board[targetX][targetY];
                    }
                    if (!visited[neighx][neighy]) {
                        queue.add(new int[]{neighx,neighy});
                    }
                }
            }
        }

        return board[targetX][targetY];
    }

    public static void main(String[] args) {
        System.out.println(new MinimumKnightMoves().minKnightMoves(5,5));
    }
}
