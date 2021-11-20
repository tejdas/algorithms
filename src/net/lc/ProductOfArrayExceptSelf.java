package net.lc;

/**
 * 238
 */
public class ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int product = 1;
        int[] result = new int[nums.length];

        for (int i = nums.length-1; i >= 0; i--) {
            product = product * nums[i];
            result[i] = product;
        }

        product = 1;
        for (int i = 0; i < nums.length-1; i++) {
            result[i] = product * result[i+1];
            product = product * nums[i];
        }

        result[nums.length-1] = product;

        return result;
    }
}
