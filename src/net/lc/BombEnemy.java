package net.lc;

public class BombEnemy {
    static class Pair {
        int numx;
        int numy;

        public Pair() {
            this.numx = -1;
            this.numy = -1;
        }
    }

    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        Pair[][] result = new Pair[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = new Pair();
            }
        }

        int maxResult = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != '0') {
                    continue;
                }

                if (result[i][j].numx == -1) {
                    int horizontalEnemyCount = 0;
                    int colIndex = j-1;
                    while (colIndex >= 0 && grid[i][colIndex] != 'W') {
                        if (grid[i][colIndex] == 'E') horizontalEnemyCount++;
                        colIndex--;
                    }

                    int leftEdge = colIndex+1;

                    colIndex = j+1;
                    while (colIndex < cols && grid[i][colIndex] != 'W') {
                        if (grid[i][colIndex] == 'E') horizontalEnemyCount++;
                        colIndex++;
                    }

                    int rightEdge = colIndex-1;
                    for (int k = leftEdge; k <= rightEdge; k++) {
                        result[i][k].numx = horizontalEnemyCount;
                    }
                }

                if (result[i][j].numy == -1) {
                    int verticalEnemyCount = 0;
                    int rowIndex = i-1;
                    while (rowIndex >= 0 && grid[rowIndex][j] != 'W') {
                        if (grid[rowIndex][j] == 'E') verticalEnemyCount++;
                        rowIndex--;
                    }

                    int topEdge = rowIndex+1;

                    rowIndex = i+1;
                    while (rowIndex < rows && grid[rowIndex][j] != 'W') {
                        if (grid[rowIndex][j] == 'E') verticalEnemyCount++;
                        rowIndex++;
                    }

                    int bottomEdge = rowIndex-1;
                    for (int k = topEdge; k <= bottomEdge; k++) {
                        result[k][j].numy = verticalEnemyCount;
                    }
                }

                maxResult = Math.max(maxResult, result[i][j].numx + result[i][j].numy);
            }
        }
        return maxResult;
    }
}
