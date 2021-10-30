package net.lc;

import java.util.Arrays;

/**
 * 55
 * Array
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
        int lastIndex = nums.length-1;

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
             * see if we can use a val between 1 and jumpVal to jump to a location where jumpResult is true.
             */
            if (jumpVal >= (lastIndex-index)) {
                jumpResult[index] = true;
                lastIndex = index;
            } else {
                jumpResult[index] = false;
            }
        }
        return jumpResult[0];
    }
}
