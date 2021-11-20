package net.lc.slidingwindow;

/**
 * 1438
 * Sliding Window
 */
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {
    public int longestSubarray(int[] nums, int limit) {
        int left = 0;
        int right;
        int maxLen = 1;

        int curMax = 0;
        int curMin = 0;
        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];

            if (val > nums[curMin] && val < nums[curMax]) {
                // nothing needs to be done
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val == nums[curMax]) {
                // update curMax to current index. The reason being, we sometimes need to shrink the window from the left.
                curMax = i;
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val == nums[curMin]) {
                // update curMin to current index. The reason being, we sometimes need to shrink the window from the left.
                curMin = i;
                right = i;
                maxLen = Math.max(maxLen, right - left + 1);
            } else if (val > nums[curMax]) {
                /**
                 * Update curMax. In addition, scan from curMax to the left for as long as the delta between curMax and
                 * the number is within limit. But, do not slide the left-edge leftwards.
                 */
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
                /**
                 * Update curMin. In addition, scan from curMin to the left for as long as the delta between curMin and
                 * the number is within limit. But, do not slide the left-edge leftwards.
                 */
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
