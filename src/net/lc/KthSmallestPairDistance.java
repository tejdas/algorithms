package net.lc;

import java.util.Arrays;

public class KthSmallestPairDistance {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);

        int largestDiff = nums[nums.length-1] - nums[0];

        int smallestDiff = Integer.MAX_VALUE;

        for (int i = 1; i < nums.length; i++) {
            smallestDiff = Math.min(smallestDiff, nums[i] - nums[i-1]);
        }

        int left = smallestDiff;
        int right = largestDiff;

        //if (left == right) return 0;

        int maxRes = Integer.MIN_VALUE;
        while (left < right) {
            System.out.println("left: " + left + " right: " + right);
            int mid = left + (right-left)/2;
            int res = getNumDiffsBelowT(nums, mid);
            if (res == k-1) {
                maxRes = Math.max(maxRes, mid);
                left = maxRes+1;
            }

            if (res > k-1) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return maxRes;
    }

    private static int getNumDiffsBelowT(int[] nums, int threshold) {
        System.out.println("getNumDiffsBelowT: " + threshold);
        int res = 0;
        int left = 0;
        int right = 1;

        while (right < nums.length) {
            if (nums[right] - nums[left] < threshold) {
                right++;
            } else {
                int dist = right-left;
                res += (dist * (dist-1))/2;
                left++;
                right = left+1;
            }
        }

        int dist = right-left;
        res += (dist * (dist-1))/2;

        //System.out.println("getNumDiffsBelowT: threshold: " + threshold + "  res: " + res);
        return res;
    }

    public static void main(String[] args) {
        {
            //int[] nums = {1,4,6,8,10, 12, 14, 19, 23};
            //System.out.println(getNumDiffsBelowT(nums, 4));
            int[] nums = { 1, 3, 1 };
            //int[] nums = {1,1,1};
            //System.out.println(new KthSmallestPairDistance().smallestDistancePair(nums, 1));
        }

        {
            int[] nums = { 62,100,4 };
            System.out.println(new KthSmallestPairDistance().smallestDistancePair(nums, 3));
        }

        {
            int[] nums = { 1,6,1};
            //System.out.println(new KthSmallestPairDistance().smallestDistancePair(nums, 3));
        }
    }
}
