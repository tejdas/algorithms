package net.lc.dfs;

/**
 * 980
 * All paths DFS
 */
public class UniquePathsIII {
    private static final int[][] narray = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    private int[][] grid = null;
    private boolean[][] visited;
    private int rows = 0, cols = 0;
    private int result = 0;

    private int startx = 0, starty = 0;
    private int endx = 0, endy = 0;
    private int numSquares = 0;
    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;

        visited = new boolean[rows][cols];

        numSquares = 0;
        result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) {
                    numSquares++;
                }

                if (grid[i][j] == 1) {
                    startx = i;
                    starty = j;
                } else if (grid[i][j] == 2) {
                    endx = i;
                    endy = j;
                }
            }
        }

        dfs(startx, starty, 1);
        return result;
    }

    private void dfs(int curx, int cury, int squaresVisited) {

        visited[curx][cury] = true;
        if (curx == endx && cury == endy) {
            if (squaresVisited == numSquares) {
                /**
                 * this means that we have visited all the squares exactly once.
                 * Count as a valid result.
                 */
                result++;
            }
        } else {
            for (int[] n : narray) {
                if (curx + n[0] >= 0 && curx + n[0] < rows && cury + n[1] >= 0 && cury + n[1] < cols) {
                    int nx = curx + n[0];
                    int ny = cury + n[1];

                    if (grid[nx][ny] != -1 && !visited[nx][ny]) {
                        dfs(nx, ny, squaresVisited + 1);
                    }
                }
            }
        }

        visited[curx][cury] = false; // enable all-paths traversal
    }
}
