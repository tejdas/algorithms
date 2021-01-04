package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 305
 * Union-Find DS
 */
public class NumberOfIslandsII {
    private static final int[][] neighs = {{-1, 0}, {1,0}, {0,-1}, {0,1}};
    static class UnionFind {
        int count = 0; // # of connected components
        int[] parent;
        int[] rank;
        int[][] grid;
        int rows, cols;

        public UnionFind(int m, int n) {
            this.rows = m;
            this.cols = n;
            count = 0;
            parent = new int[m * n];
            rank = new int[m * n];
            this.grid = new int[m][n];
        }

        public void addCell(int x, int y) {
            if (grid[x][y] == 1) return;
            grid[x][y] = 1;
            int curCell = x * cols + y;
            count++;
            parent[curCell] = curCell;
            for (int[] neigh : neighs) {
                int nx = x + neigh[0];
                int ny = y + neigh[1];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && grid[nx][ny] == 1) {
                    int neighCell = nx * cols + ny;
                    union(curCell, neighCell);
                }
            }
        }

        private int find(int i) { // path compression
            if (parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        private void union(int x, int y) { // union with rank
            int rootx = find(x);
            int rooty = find(y);
            if (rootx != rooty) {
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty;
                } else {
                    parent[rooty] = rootx; rank[rootx] += 1;
                }
                --count;
            }
        }

        public int getCount() {
            return count;
        }
    }
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m,n);

        List<Integer> result = new ArrayList<>();

        for (int[] pos : positions) {
            int curx = pos[0];
            int cury = pos[1];
            uf.addCell(curx, cury);
            result.add(uf.getCount());
        }
        return result;
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        int[][] positions = {{0,0},{0,1},{1,2},{2,1}};
        List<Integer> output = new NumberOfIslandsII().numIslands2(m, n, positions);
        System.out.println(Arrays.toString(output.toArray(new Integer[output.size()])));
    }
}
