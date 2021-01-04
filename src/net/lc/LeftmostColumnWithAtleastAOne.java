package net.lc;

import java.util.List;

/**
 * 1428
 * Greedy
 * https://leetcode.com/problems/leftmost-column-with-at-least-a-one/submissions/
 */
public class LeftmostColumnWithAtleastAOne {
    interface BinaryMatrix {
        int get(int row, int col);
        List<Integer> dimensions();
    }

    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> dimensions = binaryMatrix.dimensions();
        int rows = dimensions.get(0);
        int cols = dimensions.get(1);

        int col = cols-1;
        int row = 0;

        int minVal = Integer.MAX_VALUE;
        boolean seenOne = false;
        while (col >= 0 && row < rows) {
            int val = binaryMatrix.get(row, col);
            if (val == 0) {
                if (seenOne) {
                    /**
                     * We have seen one in this row before in col+1
                     * Update minVal, and start from minVal column in next row
                     */
                    col++;
                    minVal = Math.min(minVal, col);
                    col = minVal;
                    row++;
                    seenOne = false;
                } else {
                    /**
                     * We encountered a 0, and haven't seen one in this row yet.
                     * Move to the next row and start from minVal
                     */
                    row++;
                    col = (minVal == Integer.MAX_VALUE)? cols-1 : minVal;
                }
            } else {
                if (col == 0) return 0;
                seenOne = true;
                /**
                 * Keep moving left on the same row, until we have encountered a 0.
                 */
                col--;
            }
        }
        return (minVal == Integer.MAX_VALUE)? -1 : minVal;
    }
}
