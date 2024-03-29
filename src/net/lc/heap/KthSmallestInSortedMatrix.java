package net.lc.heap;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/submissions/
 * Priority-Queue (heap)
 * Matrix traversal
 * 378
 */
public class KthSmallestInSortedMatrix {
    static class Cell implements Comparable<Cell> {
        int x;
        int y;
        int val;

        public Cell(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo(Cell o) {
            return Integer.compare(this.val, o.val);
        }
    }
    public int kthSmallest(int[][] matrix, int k) {
        int row = 0;
        int col = 0;

        if (k == matrix.length * matrix.length) return matrix[matrix.length-1][matrix.length-1];

        /**
         * Start from top-left (0,0), and move both right and down.
         * Since a cell can be visited from top and left, maintain a processed-state so that
         * the same cell is not processed twice.
         */
        boolean[][] processed = new boolean[matrix.length][matrix.length];
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.add(new Cell(row, col, matrix[row][col]));

        int count = 0;
        while (!pq.isEmpty()) {
            Cell cur = pq.remove();

            if (processed[cur.x][cur.y]) continue;
            processed[cur.x][cur.y] = true;

            int val = cur.val;
            count++;
            if (count == k) return val;

            if (cur.x+1 < matrix.length && !processed[cur.x+1][cur.y]) {
                pq.add(new Cell(cur.x+1, cur.y, matrix[cur.x+1][cur.y]));
            }

            if (cur.y+1 < matrix.length && !processed[cur.x][cur.y+1]) {
                pq.add(new Cell(cur.x, cur.y+1, matrix[cur.x][cur.y+1]));
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        {
            int[][] matrix = { { 1, 5, 9 }, { 10, 11, 13 }, { 12, 13, 15 } };
            System.out.println(new KthSmallestInSortedMatrix().kthSmallest(matrix, 8));
        }

        {
            int[][] matrix = { { 1, 3,5 }, { 6,7,12 }, { 11,14,14 } };
            System.out.println(new KthSmallestInSortedMatrix().kthSmallest(matrix, 6));
        }
    }
}
