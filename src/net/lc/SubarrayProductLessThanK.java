package net.lc;

/**
 * 713
 * Two-pointer
 * Keep track of left and right pointer, where product of all numbers between left and right is < k
 */
public class SubarrayProductLessThanK {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) return 0;
        int result = 0;

        long product = nums[0];

        if (product < k) result++;

        int left = 0;
        for (int right = 1; right < nums.length; right++) {
            product *= nums[right];

            while (product >= k && left < right) {
                product /= nums[left];
                left++;
            }

            if (product < k) {
                result += (right - left + 1);
            }
        }
        return result;
    }
}
