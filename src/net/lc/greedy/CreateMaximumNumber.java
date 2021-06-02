package net.lc.greedy;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/create-maximum-number/submissions/
 * 321
 * Greedy
 * Stack
 */
public class CreateMaximumNumber {
    public int[] maxNumber(int[] nums0, int[] nums1, int k) {
        if (k == nums0.length + nums1.length) {
            return creatLargestNumber(nums0, nums1);
        }

        int[] len = {nums0.length, nums1.length};


        int[] smallerArray;
        int[] largerArray;

        if (len[0] <= len[1]) {
            smallerArray = nums0;
            largerArray = nums1;
        } else {
            smallerArray = nums1;
            largerArray = nums0;
        }

        int minLen = Math.min(k, Math.min(len[0], len[1]));

        int[] maxRes = null;
        for (int k0 = minLen; k0 >= 0; k0--) {
            int k1 = k - k0;

            int[] res0 = creatLargestNumber(smallerArray, k0);
            int[] res1 = creatLargestNumber(largerArray, k1);

            int[] res = creatLargestNumber(res0, res1);

            if (maxRes == null) {
                maxRes = res;
            } else {
                maxRes = getLargest(maxRes, res);
            }
        }

        return maxRes;
    }

    private static int[] getLargest(int[] nums1, int[] nums2) {
        int index1 = 0;
        int index2 = 0;

        while (index1 < nums1.length && nums1[index1] == nums2[index2]) {
            index1++;
            index2++;
        }

        if (index1 == nums1.length) return nums1;

        if (nums1[index1] > nums2[index2]) return nums1; else return nums2;
    }

    /**
     * create largest number of k digits from an array of integers.
     * Use stack
     * The numbers must be in the order in which they appear in the array.
     *
     * @param nums
     * @param k
     * @return
     */
    private static int[] creatLargestNumber(int[] nums, int k) {
        if (k == 0) return new int[] {};
        int[] result = new int[k];

        int toBeRemoved = nums.length - k;
        int removed = 0;

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            while (removed < toBeRemoved && !stack.isEmpty() && val > stack.peek().intValue()) {
                stack.pop();
                removed++;
            }

            stack.push(val);
        }
        int index = 0;
        for (int val : stack) {
            if (index == k) break;
            result[index++] = val;
        }
        return result;
    }

    private static int[] creatLargestNumber(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];

        int index1 = 0;
        int index2 = 0;
        int index = 0;

        while (index1 < nums1.length && index2 < nums2.length) {
            if (nums1[index1] > nums2[index2]) {
                result[index++] = nums1[index1++];
            } else if (nums1[index1] < nums2[index2]) {
                result[index++] = nums2[index2++];
            } else {
                /**
                 * while the current matching digits are the same, keep moving
                 * both indices until (a) the digits are not the same or (b) one
                 * of the indices reaches end of array.
                 *
                 * In case of (a), pick the next element from the array that has larger value.
                 * In case of (b), pick the next element from array that we did not reach the end of.
                 */
                int idx1 = index1;
                int idx2 = index2;

                while (idx1 < nums1.length && idx2 < nums2.length && nums1[idx1] == nums2[idx2]) {
                    idx1++;
                    idx2++;
                }

                if (idx1 == nums1.length) {
                    result[index++] = nums2[index2++];
                } else if (idx2 == nums2.length) {
                    result[index++] = nums1[index1++];
                } else {
                    if (nums1[idx1] > nums2[idx2]) {
                        result[index++] = nums1[index1++];
                    } else {
                        result[index++] = nums2[index2++];
                    }
                }
            }
        }

        while (index1 < nums1.length) {
            result[index++] = nums1[index1++];
        }
        while (index2 < nums2.length) {
            result[index++] = nums2[index2++];
        }
        return result;
    }

    public static void main(String[] args) {
        /*
        {
            int[] nums0 = { 3, 4, 6, 5 };
            int[] nums1 = { 9, 1, 2, 5, 8, 3 };

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 5);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 6,7 };
            int[] nums1 = { 6,0,4 };

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 5);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 3,9 };
            int[] nums1 = { 8,9 };

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 3);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums = { 9, 1, 2, 5, 8, 3 };
            //int[] res = creatLargestNumber(nums, 1);
            //System.out.println(Arrays.toString(res));
        }

        {
            //int[] nums = Util.generateRandomNumbers(new Random(), 10);
            //System.out.println(Arrays.toString(nums));
            //int[] res = creatLargestNumber(nums, 4);
            //System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 3, 4, 6, 5 };
            int[] nums1 = { 9, 1, 2, 5, 8, 3 };

            //int[] res = creatLargestNumber(nums0, nums1);
            //System.out.println(Arrays.toString(res));
        }
*/
        {
            int[] nums0 = { 2,5,6,4,4,0 };
            int[] nums1 = { 7,3,8,0,6,5,7,6,2 };

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 15);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 2,5,6,4,4,0,3 };
            int[] nums1 = { 7,3,8,0,6,5,7,6,2 };

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 16);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 5,0,2,1,0,1,0,3,9,1,2,8,0,9,8,1,4,7,3 };
            int[] nums1 = { 7,6,7,1,0,1,0,5,6,0,5,0 };
            // expected:
            //[7,6,7,5,1,0,2,1,0,1,0,5,6,0,5,0,1,0,3,9,1,2,8,0,9,8,1,4,7,3,0]

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 31);
            System.out.println(Arrays.toString(res));
        }

        {
            int[] nums0 = { 5,8,3,7 };
            int[] nums1 = { 5,8,3,9};

            int[] res = new CreateMaximumNumber().maxNumber(nums0, nums1, 8);
            System.out.println(Arrays.toString(res));
        }
    }
}
