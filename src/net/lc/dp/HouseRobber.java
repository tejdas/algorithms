package net.lc.dp;

/**
 * 198
 * Dynamic Programming
 */
public class HouseRobber {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] maxRob = new int[nums.length];

        maxRob[0] = nums[0];
        if (nums.length > 1) {
            maxRob[1] = Math.max(maxRob[0], nums[1]);
        }

        for (int i = 2; i < nums.length; i++) {
            maxRob[i] = Math.max(maxRob[i-2] + nums[i], maxRob[i-1]);
        }
        return maxRob[nums.length-1];
    }
}
