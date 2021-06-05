package net.lc.slidingwindow;

/**
 * 1004
 * Sliding Window
 */
public class MaxConsecutiveOnesIII {
    class Solution {
        public int longestOnes(int[] array, int K) {
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
                        while (array[left] ==1) left++;

                        left++;
                        right++;
                        result = Math.max(result, right-left+1);
                    } else {
                        numZeros++;
                        right++;
                        result = Math.max(result, right-left+1);
                    }
                }
            }
            return result;
        }
    }
}
