package net.lc.slidingwindow;

/**
 * https://leetcode.com/problems/sliding-window-maximum/submissions/
 * 239
 * Sliding Window
 * Some of the cases are TLE
 * Use a PriorityQueue instead
 */
public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {

        if (nums == null || nums.length == 0) {
            return new int[] {};
        }

        return calcMaxNumber(nums, k);
    }

    private int[] calcMaxNumber(int[] array, int k) {
        int[] result = new int[array.length-k+1];
        int j = 0;


        int maxIndex = getMaxIndex(array, 0, k);

        result[j++] = array[maxIndex];

        for (int i = k; i < array.length; i++) {
            int evictedIndex = i-k;
            if (evictedIndex == maxIndex) {
                maxIndex = getMaxIndex(array, evictedIndex+1, k);
            } else {
                if (array[i] >= array[maxIndex]) {
                    maxIndex = i;
                }
            }
            result[j++] = array[maxIndex];;
        }

        return result;
    }

    private int getMaxIndex(int[] array, int startPos, int windowSize) {
        if (windowSize == 0) {
            return startPos;
        }
        int maxNumber = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = startPos; i < (startPos+windowSize); i++) {
            if (array[i] > maxNumber) {
                maxNumber = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
