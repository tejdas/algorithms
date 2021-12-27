package net.lc;

import java.util.Arrays;

/**
 * 915
 * Greedy
 */
public class ParitionArrayIntoDisjointIntervals {
    public int partitionDisjoint(int[] nums) {
        if (nums.length == 0) return 0;

        int[] maxFromLeft = new int[nums.length];
        int[] minFromRight = new int[nums.length];

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {

            max = Math.max(max, nums[i]);
            maxFromLeft[i] = max;
        }

        for (int i = nums.length-1; i >= 0; i--) {
            min = Math.min(min, nums[i]);
            minFromRight[i] = min;
        }

        System.out.println(Arrays.toString(maxFromLeft));
        System.out.println(Arrays.toString(minFromRight));

        for (int i = 0; i < nums.length-1; i++) {
            if (maxFromLeft[i] <= minFromRight[i+1]) {
                return i+1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        {
            int[] input = { 5, 0, 3, 8, 6 };

            System.out.println(new ParitionArrayIntoDisjointIntervals().partitionDisjoint(input));
        }

        {
            int[] input = {1,1,1,0,6,12 };

            System.out.println(new ParitionArrayIntoDisjointIntervals().partitionDisjoint(input));
        }
    }
}
