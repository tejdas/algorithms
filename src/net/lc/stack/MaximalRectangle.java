package net.lc.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * 85
 * Monotonic Stack
 */
public class MaximalRectangle {
    private int largestRectangleArea(int[] heights) {
        int maxResult = 0;

        Stack<Integer> stack = new Stack<>();
        for (int val : heights) {

            if (val == 0) {
                /**
                 * found a gap
                 */
                maxResult = Math.max(maxResult, calc(stack));
                stack.clear();
                continue;
            }

            if (stack.isEmpty()) {
                stack.push(val);
                continue;
            }

            /**
             * found a bigger value
             */
            if (val >= stack.peek()) {
                stack.push(val);
                continue;
            }

            /**
             * found a smaller value, so we need to compute the maxResult
             */
            int count = 0;
            int minValRemoved = Integer.MAX_VALUE;
            while (!stack.isEmpty() && val < stack.peek()) {
                int ejected = stack.pop();
                minValRemoved = Math.min(ejected, minValRemoved);
                count++;
                maxResult = Math.max(maxResult, count * minValRemoved);
            }

            /**
             * push val for each item that was removed above from stack
             */
            for (int i = 0; i < count; i++) {
                stack.push(val);
            }
            stack.push(val);
        }

        maxResult = Math.max(maxResult, calc(stack));
        return maxResult;
    }

    private int calc(Stack<Integer> stack) {
        if (stack.isEmpty()) return 0;

        int maxResult = 0;

        int count = 0;
        int minVal = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            int val = stack.pop();
            minVal = Math.min(minVal, val);
            count++;
            maxResult = Math.max(maxResult, count * minVal);
        }
        return maxResult;
    }

    public int maximalRectangle(char[][] matrix) {

        int rows = matrix.length;
        if (rows == 0) return 0;
        int cols = matrix[0].length;
        if (cols == 0) return 0;

        int[] prev = new int[cols];
        Arrays.fill(prev, 0);

        int result = 0;

        for (int i = 0; i < rows; i++) {
            char[] curRow = matrix[i];

            int[] cur = new int[cols];

            for (int j = 0; j < cols; j++) {
                if (curRow[j] == '1') {
                    cur[j] = prev[j]+1;
                } else {
                    cur[j] = 0;
                }
            }

            result = Math.max(result, largestRectangleArea(cur));
            prev = cur;
        }
        return result;
    }
}
