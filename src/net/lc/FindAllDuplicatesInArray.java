package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 442
 * Negate a number to remember visited number (without using a HashMap)
 * No extra memory.
 */
public class FindAllDuplicatesInArray {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();

        /**
         * Since all the values are between 1 and n (inclusive),
         * when we see a number, use the number-1 as index to flip
         * the value at number-1 to negative. If it's already negative,
         * it means we have seen the number before (duplicate).
         */
        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]);
            if (nums[val-1] < 0) {
                result.add(val);
            } else {
                nums[val-1] = - nums[val-1];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> result = new FindAllDuplicatesInArray().findDuplicates(new int[] {4,3,2,7,8,2,3,1});
        System.out.println(Arrays.toString(result.toArray(new Integer[result.size()])));
    }
}
