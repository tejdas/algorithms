package net.lc;

/**
 * 256
 * https://leetcode.com/problems/paint-house/
 * Dynamic Programming
 */
public class PaintHouse {
    public int minCost(int[][] costs) {

        int count = costs.length;
        if (count == 0) return 0;

        int[][] minCost = new int[count][3];

        for (int j = 0; j < 3; j++)
            minCost[0][j] = costs[0][j];

        for (int i = 1; i < count; i++) {
            minCost[i][0] = costs[i][0] + Math.min(minCost[i-1][1], minCost[i-1][2]);

            minCost[i][1] = costs[i][1] + Math.min(minCost[i-1][0], minCost[i-1][2]);

            minCost[i][2] = costs[i][2] + Math.min(minCost[i-1][1], minCost[i-1][0]);
        }
        return Math.min(minCost[count-1][0], Math.min(minCost[count-1][1], minCost[count-1][2]));
    }
}
