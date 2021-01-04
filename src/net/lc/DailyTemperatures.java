package net.lc;

import java.util.Stack;

/**
 * 739
 * Stack
 */
public class DailyTemperatures {
    static class Pair {
        int val;
        int index;

        public Pair(int val, int index) {
            this.val = val;
            this.index = index;
        }
    }
    public int[] dailyTemperatures(int[] T) {
        if (T == null) return null;

        Stack<Pair> stack = new Stack<>();

        int[] result = new int[T.length];

        for (int i = 0; i < T.length; i++) {
            Pair p = new Pair(T[i], i);

            if (stack.isEmpty()) {
                stack.push(p);
                continue;
            }

            while (!stack.isEmpty() && T[i] > stack.peek().val) {
                /**
                 * THe next warmer/higher temperature day for the popped-element
                 * is current index i. So number of days until warmer temp. is
                 * i - popped.index
                 */
                Pair popped = stack.pop();
                result[popped.index] = (i - popped.index);
            }

            stack.push(p);
        }

        /**
         * The remaining values in the stack are in non-decreasing order, meaning, they
         * will NOT see a warmer day in the future, so set it to 0.
         */
        while (!stack.isEmpty()) {
            Pair popped = stack.pop();
            result[popped.index] = 0;
        }
        return result;
    }
}
