package net.lc;

/**
 * https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
 * Two-pointer
 */
public class LargestContinuousSubArray {
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
    public static void main(String[] args) {
        {
            int[] array = { 10, 1, 2, 4, 7, 2 };
            System.out.println(new LargestContinuousSubArray().longestSubarray(array, 5));
        }

        {
            int[] array = {4,2,2,2,4,4,2,2 };
            System.out.println(new LargestContinuousSubArray().longestSubarray(array, 0));
        }

        {
            int[] array = {1,5,6,7,8,10,6,5,6};
            System.out.println(new LargestContinuousSubArray().longestSubarray(array, 4));
        }

        {
            int[] array = {8,2,4,7};
            System.out.println(new LargestContinuousSubArray().longestSubarray(array, 4));
        }
    }
}
