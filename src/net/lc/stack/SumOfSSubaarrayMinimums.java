package net.lc.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 * Stack solution
 * NLE
 * PLE
 */
public class SumOfSSubaarrayMinimums {
    public int sumSubarrayMins(int[] array) {
        if (array == null || array.length == 0) return 0;

        int[] pleArray = getPreviousSmallerElement(array);
        int[] nleArray = getNextSmallerElement(array);

        long res = 0;

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            int ple = pleArray[i];
            int nle = nleArray[i];

            /**
             * In all the subarray ranges [m,n], where m >= ple and n <= nle,
             * current element is the minimum.
             * Therefore, calculate number of subarrays
             */
            int numSubarrays = Math.abs(nle-i) * Math.abs(i-ple);

            res += val * numSubarrays;
        }

        long retval = res % 1000000007;
        return (int) retval;
    }

    /**
     * Returns an output array, such that:
     * output[i] = index of the next smaller value;
     * output[i] = j where j > i and array[j] <= array[i]
     * @param array
     * @return
     */
    private int[] getNextSmallerElement(int[] array) {
        int[] res = new int[array.length];
        Arrays.fill(res, array.length);

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            while (!stack.isEmpty() && val <= array[stack.peek()]) {
                int idx = stack.pop();
                res[idx] = i;
            }
            stack.push(i);
        }

        //System.out.println(Arrays.toString(res));
        return res;
    }

    private int[] getPreviousSmallerElement(int[] array) {
        int[] res = new int[array.length];
        Arrays.fill(res, -1);

        Stack<Integer> stack = new Stack<>();

        for (int i = array.length-1; i >= 0; i--) {
            int val = array[i];
            while (!stack.isEmpty() && val < array[stack.peek()]) {
                int idx = stack.pop();
                res[idx] = i;
            }
            stack.push(i);
        }

        //System.out.println(Arrays.toString(res));
        return res;
    }
}
