package net.lc.greedy;

import java.util.Arrays;

/**
 * 55
 * Array
 * Greedy
 * DP
 */
public class JumpGame {
    public boolean canJump(int[] nums) {
        /**
         * memoized array
         * jumpResult[i] = true, if there is a way to go from
         * ith position to the end.
         */
        boolean[] jumpResult = new boolean[nums.length];
        Arrays.fill(jumpResult, false);

        jumpResult[nums.length-1] = true;
        /**
         * keep track of lastSeenReachableIndex: jumpResult[lastSeenReachableIndex] = true
         */
        int lastSeenReachableIndex = nums.length-1;

        for (int index = nums.length-2; index >= 0; index--) {
            int jumpVal = nums[index];
            if (jumpVal == 0) {
                /**
                 * cannot jump
                 */
                jumpResult[index] = false;
                continue;
            }

            /**
             * see if we can use jumpVal to reach (or go past) lastSeenReachableIndex
             */
            if ((jumpVal + index) >= lastSeenReachableIndex) {
                jumpResult[index] = true;
                /**
                 * Update lastSeenReachableIndex
                 */
                lastSeenReachableIndex = index;
            } else {
                jumpResult[index] = false;
            }
        }
        return jumpResult[0];
    }
}
