package net.lc.slidingwindow;

import java.util.Arrays;

/**
 * 424
 * Sliding Window
 */
public class LongestRepeatingCharReplacement {
    public int characterReplacement(String s, int k) {
        char[] sarray = s.toCharArray();
        int result = 0;

        for (char c='A'; c <= 'Z'; c++) {
            /**
             * For every char in the input, pick one char at a time.
             * Create an array with all occurrences of that char marked as 1
             * rest are 0.
             * Then it becomes a problem of Max consecutive Ones.
             */
            int[] array = new int[sarray.length];
            Arrays.fill(array, 0);
            for (int index = 0; index < sarray.length; index++) {
                if (sarray[index] == c) {
                    array[index] = 1;
                }
            }

            result = Math.max(result, largestWindowWithAtmostKZeros(array, k));
        }
        return result;
    }

    /**
     * Find the largest window with atmost K 0s.
     * If we flip the 0s, then we get the answer for above.
     * @param array
     * @param K
     * @return
     */
    private int largestWindowWithAtmostKZeros(int[] array, int K) {
        int result = 0;

        int left = 0;
        int right = -1;
        int numZeros = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                right++;
                result = Math.max(result, right-left+1);
            } else {
                if (numZeros == K) {
                    /**
                     * we are already at K 0s. Shrink the left edge of the window until we go past one zero.
                     */
                    while (array[left] ==1) left++;

                    /**
                     * Go past one zero
                     */
                    left++;
                    /**
                     * Now add zero on the right edge.
                     */
                    right++;
                    result = Math.max(result, right-left+1);
                } else {
                    /**
                     * we are still below K zeros. So, accommodate one more.
                     */
                    numZeros++;
                    right++;
                    result = Math.max(result, right-left+1);
                }
            }
        }
        return result;
    }
}
