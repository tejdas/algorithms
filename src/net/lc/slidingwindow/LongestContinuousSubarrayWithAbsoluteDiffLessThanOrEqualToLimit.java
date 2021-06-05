package net.lc.slidingwindow;

/**
 * 1438
 * Sliding Window
 */
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {
    public int longestSubarray(int[] nums, int limit) {
        int left = 0;
        int right = 0;
        int maxLen = 1;

        int curMax = 0;
        int curMin = 0;
        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];

            if (val > nums[curMin] && val < nums[curMax]) {
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val == nums[curMax]) {
                curMax = i;
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val == nums[curMin]) {
                curMin = i;
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val > nums[curMax]) {
                curMax = i;
                right = i;

                // minValue >= nums[curMax] - limit
                int newLeft = right;
                int curMinIndex = newLeft;
                while (newLeft >= left && (nums[right] - nums[newLeft]) <= limit) {
                    if (nums[newLeft] < nums[curMinIndex]) {
                        curMinIndex = newLeft;
                    }
                    newLeft--;
                }

                newLeft++;
                left = newLeft;
                curMin = curMinIndex;
                maxLen = Math.max(maxLen, right - left + 1);
            } else { // val < nums[curMin]
                curMin = i;
                right = i;

                // maxValue <= nums[curMin] + limit
                int newLeft = right;
                int curMaxIndex = newLeft;
                while (newLeft >= left && (nums[newLeft] - nums[right]) <= limit) {
                    if (nums[newLeft] > nums[curMaxIndex]) {
                        curMaxIndex = newLeft;
                    }
                    newLeft--;
                }

                newLeft++;
                left = newLeft;
                curMax = curMaxIndex;
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }
        return maxLen;
    }
}
