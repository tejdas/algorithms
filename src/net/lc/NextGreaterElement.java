package net.lc;

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

        int index = 0;
        for (int idx = 0; idx < nums.length*2-1; idx++) {
            int val = nums[index];

            if (!stack.isEmpty()) {
                if (index == stack.peek().index) {
                    stack.pop();
                    resultList[index] = -1;
                } else if (val > stack.peek().val) {
                    while (!stack.isEmpty() && val > stack.peek().val) {
                        SPair spair = stack.pop();
                        resultList[spair.index] = val;
                    }
                }
            }
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
