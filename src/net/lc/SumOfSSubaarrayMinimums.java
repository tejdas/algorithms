package net.lc;

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

        int[] pleArray = getPLE(array);
        int[] nleArray = getNLE(array);

        long res = 0;

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            int ple = pleArray[i];
            int nle = nleArray[i];

            int offset = Math.abs(nle-i) * Math.abs(i-ple);
            //System.out.println("val: " + val + " offset: " + offset);

            res += val * offset;
        }

        long retval = res % 1000000007;
        return (int) retval;
    }

    private int[] getNLE(int[] array) {
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

    private int[] getPLE(int[] array) {
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
