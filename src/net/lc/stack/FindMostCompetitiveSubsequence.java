package net.lc.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/find-the-most-competitive-subsequence/
 * 1673
 * Monotonic Stack
 */
public class FindMostCompetitiveSubsequence {
    public int[] mostCompetitive(int[] nums, int k) {
        if (k == 0) return new int[0];
        if (k == nums.length) return nums;

        Stack<Integer> stack = new Stack<>();

        /**
         * Maintain the smaller numbers on the stack as they are seen.
         * Replace higher numbers in the stack with a new lower number only if there are more
         * numbers to be processed than there is space in stack.
         *
         * For example, if stack has less than k elements, and we have more numbers to process than
         * stack has space, then pop higher values from stack.
         *
         * If stack size is at k, and more values are there to be processed, then also pop higher values from stack.
         */
        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            while (!stack.isEmpty() && val < stack.peek()) {
                int toProcess = (nums.length - i);
                int stackSpace = k - stack.size();
                if (toProcess > stackSpace) stack.pop();
                else break;
            }

            if (stack.size() < k) {
                stack.push(val);
            }
        }

        int[] res = new int[k];
        int index = 0;
        for (int val : stack) {
            res[index++] = val;
        }
        return res;
    }

    public static void main(String[] args) {
        {
            int[] res = new FindMostCompetitiveSubsequence().mostCompetitive(new int[] { 3, 5, 2, 6 }, 2);
            System.out.println(Arrays.toString(res));
        }
        {
            int[] res = new FindMostCompetitiveSubsequence().mostCompetitive(new int[] { 2,4,3,3,5,4,9,6 }, 4);
            System.out.println(Arrays.toString(res));
        }
        {
            int[] res = new FindMostCompetitiveSubsequence().mostCompetitive(new int[] { 71,18,52,29,55,73,24,42,66,8,80,2 }, 3);
            System.out.println(Arrays.toString(res));
        }
    }
}
