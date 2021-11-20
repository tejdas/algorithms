package net.lc.dp;

/**
 * 213
 */
public class HouseRobberII {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        if (nums.length == 1)
            return nums[0];

        if (nums.length == 2)
            return Math.max(nums[0], nums[1]);

        int[] maxRobWith0 = new int[nums.length];
        int[] maxRobWithout0 = new int[nums.length];

        maxRobWith0[0] = nums[0];
        maxRobWithout0[0] = 0;

        if (nums.length > 1) {
            maxRobWith0[1] = maxRobWith0[0];
            maxRobWithout0[1] = nums[1];
        }

        for (int i = 2; i < nums.length - 1; i++) {
            maxRobWith0[i] = Math.max(maxRobWith0[i - 1], maxRobWith0[i - 2] + nums[i]);
            maxRobWithout0[i] = Math.max(maxRobWithout0[i - 1], maxRobWithout0[i - 2] + nums[i]);
        }

        int lastIndex = nums.length - 1;

        maxRobWith0[lastIndex] = maxRobWith0[lastIndex - 1];
        maxRobWithout0[lastIndex] = Math.max(maxRobWithout0[lastIndex - 1], maxRobWithout0[lastIndex - 2] + nums[lastIndex]);
        return Math.max(maxRobWith0[lastIndex], maxRobWithout0[lastIndex]);
    }
}

