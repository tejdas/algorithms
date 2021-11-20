package net.lc.dp;

public class MaximumSubarray {
    public int maxSubArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int maxSum = array[0];

        int[] dpsum = new int[array.length];
        dpsum[0] = array[0];

        for (int i = 1; i < array.length; i++) {
            if (dpsum[i-1] < 0) {
                dpsum[i] = array[i];
            } else {
                dpsum[i] = array[i] + dpsum[i-1];
            }

            maxSum = Math.max(maxSum, dpsum[i]);
        }

        return maxSum;
    }
}
