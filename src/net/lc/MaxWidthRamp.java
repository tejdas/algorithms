package net.lc;

import java.util.Stack;

/**
 * 962
 * Monotonic stack
 */
public class MaxWidthRamp {
    public int maxWidthRamp(int[] nums) {
        int maxWidth = 0;

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            if (stack.isEmpty()) {
                stack.push(i);
                continue;
            }

            if (nums[i] < nums[stack.peek()]) {
                stack.push(i);
                continue;
            }

            Stack<Integer> ts = new Stack<>();
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                int popped = stack.pop();
                maxWidth = Math.max(maxWidth, i-popped);
                ts.push(popped);
            }

            while (!ts.isEmpty()) {
                stack.push(ts.pop());
            }

        }
        return maxWidth;
    }

    public static void main(String[] args) {
        {
            int[] nums = { 6, 0, 8, 2, 1, 5 };
            System.out.println(new MaxWidthRamp().maxWidthRamp(nums));
        }

        {
            int[] nums = { 9,8,1,0,1,9,4,0,4,1};
            System.out.println(new MaxWidthRamp().maxWidthRamp(nums));
        }
    }
}
