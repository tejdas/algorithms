package net.lc;

import java.util.Stack;

/**
 * 84
 * Stack
 */
public class LargestRectangleInHistogram {
    /**
     * Keeps track of a slab of Histograms or equal height in the Stack,
     * rather than keeping individual histograms in the stack.
     * Using this technique improved stack operation (from O(n) to O(1))
     */
    static class Rect {
        int height = 0;
        int width = 0;

        public Rect(int height, int width) {
            this.height = height;
            this.width = width;
        }
    }
    public int largestRectangleArea(int[] heights) {
        int largestArea = 0;

        Stack<Rect> stack = new Stack<>();
        for (int val : heights) {

            if (val == 0) {
                /**
                 * found a gap
                 */
                largestArea = Math.max(largestArea, calc(stack));
                stack.clear();
                continue;
            }

            if (stack.isEmpty()) {
                stack.push(new Rect(val, 1));
                continue;
            }

            if (val == stack.peek().height) {
                /**
                 * found an equal value
                 */
                stack.peek().width++;
                continue;
            }

            if (val > stack.peek().height) {
                /**
                 * found a bigger value
                 */
                stack.push(new Rect(val, 1));
                continue;
            }

            /**
             * found a smaller value, so we need to compute the largestArea
             */
            int count = 0;
            int minValRemoved = Integer.MAX_VALUE;
            while (!stack.isEmpty() && val < stack.peek().height) {
                Rect poppedRect = stack.pop();
                int popped = poppedRect.height;
                minValRemoved = Math.min(minValRemoved, popped);
                count += poppedRect.width;
                largestArea = Math.max(largestArea, count * minValRemoved);
            }

            /**
             * Trim each of the popped histogram to the level of val, by
             * pushing val for each item that was removed above from stack.
             * Plus, push the current val
             */
            stack.push(new Rect(val, count+1));
        }

        largestArea = Math.max(largestArea, calc(stack));
        return largestArea;
    }

    /**
     * Called when a gap is found (val=0), or after processing all histograms.
     * Calculate the largest rectangle since last gap.
     *
     * @param stack
     * @return
     */
    private int calc(Stack<Rect> stack) {
        if (stack.isEmpty()) return 0;

        int maxResult = 0;

        int count = 0;
        int minValRemoved = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            Rect poppedRect = stack.pop();
            int val = poppedRect.height;
            minValRemoved = Math.min(minValRemoved, val);
            count += poppedRect.width;
            maxResult = Math.max(maxResult, count * minValRemoved);
        }

        return maxResult;
    }
}
