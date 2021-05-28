package net.lc.stack;

import java.util.Stack;

/**
 * 503
 * Stack
 */
public class NextGreaterElement {
    static class SPair {
        int index;
        int val;

        public SPair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
    public int[] nextGreaterElements(int[] nums) {
        if (nums == null) return null;
        if (nums.length == 0) return new int[0];

        int[] resultList = new int[nums.length];
        for (int idx = 0; idx < nums.length; idx++) {
            resultList[idx] = nums[idx];
        }
        Stack<SPair> stack = new Stack<>();

        /**
         * Traverse the circular array twice
         */
        int index = 0;
        for (int idx = 0; idx < nums.length*2-1; idx++) {
            int val = nums[index];

            if (!stack.isEmpty()) {
                if (index == stack.peek().index) {
                    /**
                     * we have traversed one full circle, and back to same index.
                     */
                    stack.pop();
                    resultList[index] = -1;
                } else if (val > stack.peek().val) {
                    while (!stack.isEmpty() && val > stack.peek().val) {
                        /**
                         * Mark popped elements' largest next index to be current index.
                         */
                        SPair spair = stack.pop();
                        resultList[spair.index] = val;
                    }
                }
            }
            /**
             * Store the current index in stack for later processing.
             * Move the index by 1 modulo array-len.
             */
            stack.push(new SPair(index, val));
            index = (index + 1) % nums.length;
        }

        for (int idx = 0; idx < nums.length; idx++) {
            if (resultList[idx] == nums[idx]) {
                resultList[idx] = -1;
            }
        }
        return resultList;
    }
}
