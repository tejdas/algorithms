package net.lc.greedy;

/**
 * 45
 * Greedy
 * Two-pointer
 */
public class JumpGameII {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums[0] == 0) return 0;

        if (nums.length == 1 && nums[0] > 0) return 0;

        if (nums[0] >= nums.length-1) {
            return 1;
        }

        int jumpCount = 0;
        int rightEdge = 0;
        int farthestJump = 0;

        /**
         * At every pos, see how far you can jump.
         * Keep track of the maxFarthestJump
         * Once you reach a right-edge (with the previous jump),
         * pick the maxFarthestJump as right-edge.
         * (because, maxFarthestJump is the cumulative farthest point
         * that is computed at every pos leading to right-edge.
         */
        for (int i = 0; i < nums.length-1; i++) {
            int jumpFromCurrentPos = i + nums[i];
            farthestJump = Math.max(farthestJump, jumpFromCurrentPos);

            if (i == rightEdge) {
                /**
                 * We have reached the right-most-edge from the last jump.
                 * Now, time to add another jump from one of the past encountered
                 * positions, that would take us to farthestJump.
                 */
                jumpCount++;
                rightEdge = farthestJump;
            }
        }
        return jumpCount;
    }
}
