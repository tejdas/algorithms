package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 329
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/submissions/
 * DFS All-path traversal
 */
public class LongestIncreasingPathInMatrix {
    static class Pos {
        int x, y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Pos{" + "x=" + x + ", y=" + y + '}';
        }
    }

    private int rows;
    private int cols;
    int[][] matrix;
    int maxDepth = 0;

    private List[][] neighborMap;

    private int[][] distanceMap;

    public int longestIncreasingPath(int[][] matrix) {

        if (matrix == null) {
            return 0;
        }

        this.matrix = matrix;
        rows = matrix.length;
        if (rows == 0) return 0;
        cols = matrix[0].length;
        if (cols == 0) return 0;

        neighborMap = new List[rows][cols];

        distanceMap = new int[rows][cols];

        List<Pos> startNodes = getStartNodes();
        maxDepth = 0;

        for (Pos startNode : startNodes) {
            findLongestPath(startNode);
        }
        return maxDepth;
    }

    private List<Pos> getNeighbors(int x, int y) {

        if (neighborMap[x][y] != null) {
            return neighborMap[x][y];
        }

        List<Pos> neighbors = new ArrayList<>();

        // x-1, y
        if (x-1 >= 0 && matrix[x-1][y] > matrix[x][y]) {
            neighbors.add(new Pos(x-1, y));
        }

        // x+1, y
        if (x+1 < rows && matrix[x+1][y] > matrix[x][y]) {
            neighbors.add(new Pos(x+1, y));
        }

        // x, y-1
        if (y-1 >= 0 && matrix[x][y-1] > matrix[x][y]) {
            neighbors.add(new Pos(x, y-1));
        }

        // x, y+1
        if (y+1 < cols && matrix[x][y+1] > matrix[x][y]) {
            neighbors.add(new Pos(x, y+1));
        }

        neighborMap[x][y] = neighbors;
        return neighbors;
    }

    private boolean isStartNode(int x, int y) {

        // x-1, y
        if (x-1 >= 0 && matrix[x-1][y] < matrix[x][y]) {
            return false;
        }

        // x+1, y
        if (x+1 < rows && matrix[x+1][y] < matrix[x][y]) {
            return false;
        }

        // x, y-1
        if (y-1 >= 0 && matrix[x][y-1] < matrix[x][y]) {
            return false;
        }

        // x, y+1
        if (y+1 < cols && matrix[x][y+1] < matrix[x][y]) {
            return false;
        }

        return true;
    }

    private List<Pos> getStartNodes() {
        List<Pos> list = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isStartNode(i, j)) {
                    list.add(new Pos(i, j));
                }
            }
        }
        return list;
    }

    private void findLongestPath(Pos initPos) {
        //System.out.println("findLongestPath: " + initPos);
        boolean[][] visited = new boolean[rows][cols];
        int depth = findLongestPath(initPos, visited);
        maxDepth = Math.max(maxDepth, depth);
    }

    /**
     * DFS All-path traversal
     * @param curPos
     * @param visited
     * @return
     */
    private int findLongestPath(Pos curPos, boolean[][] visited) {

        int existingDepth = distanceMap[curPos.x][curPos.y];
        if (existingDepth > 0) {
            return existingDepth;
        }
        visited[curPos.x][curPos.y] = true;

        List<Pos> neighbors = getNeighbors(curPos.x, curPos.y);

        int maxD = 1;
        if (!neighbors.isEmpty()) {
            for (Pos pos : neighbors) {
                if (!visited[pos.x][pos.y]) {
                    int depth = findLongestPath(pos, visited);
                    maxD = Math.max(maxD, depth+1);
                }
            }
        }

        distanceMap[curPos.x][curPos.y] = maxD;
        visited[curPos.x][curPos.y] = false; //enable all-path traversal
        return maxD;
    }
}
