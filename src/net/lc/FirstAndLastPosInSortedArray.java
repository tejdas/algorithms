package net.lc;

import java.util.Arrays;

/**
 * 34
 */
public class FirstAndLastPosInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int index = Arrays.binarySearch(nums, target);

        if (index < 0) {
            return new int[] {-1, -1};
        } else {
            int[] output = new int[2];

            /**
             * Binary search between 0 and index for the first occurrence of target
             */
            int lo = 0;
            int hi = index;

            while (lo <= hi) {
                int mid = (lo + hi) / 2;

                if (nums[mid] == target) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            output[0] = lo;

            /**
             * Binary search between index and len-1 for the last occurrence of target
             */
            lo = index;
            hi = nums.length-1;

            while (lo <= hi) {
                int mid = (lo + hi) / 2;

                if (nums[mid] == target) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            output[1] = hi;
            return output;
        }
    }
    public static void main(String[] args) {
        /*
        {
            int[] array = { 5,7,7,8,8,10 };

            int[] res = new FirstAndLastPosInSortedArray().searchRange(array, 8);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] array = { 1, 1, 1, 2, 4, 4, 4, 5, 5, 5, 5, 6, 7, 8, 8, 9, 9, 9, 9, 9, 9, 10 };

            int[] res = new FirstAndLastPosInSortedArray().searchRange(array, 9);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] array = { 1 };

            int[] res = new FirstAndLastPosInSortedArray().searchRange(array, 0);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] array = { 1 };

            int[] res = new FirstAndLastPosInSortedArray().searchRange(array, 1);
            System.out.println(Arrays.toString(res));
        }
*/
        {
            int[] array = { 1, 4};

            int[] res = new FirstAndLastPosInSortedArray().searchRange(array, 4);
            System.out.println(Arrays.toString(res));
        }
    }
}
