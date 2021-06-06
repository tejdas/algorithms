package net.lc.slidingwindow;

import java.util.Arrays;
import java.util.PriorityQueue;

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

    static class Entry implements Comparable<Entry> {
        int index;
        int val;

        public Entry(int index, int val) {
            this.index = index;
            this.val = val;
        }

        @Override
        public int compareTo(Entry o) {
            if (o.val != this.val) return Integer.compare(o.val, this.val);
            return Integer.compare(this.index, o.index);
        }
    }

    private int[] calcMaxNumber(int[] array, int k) {
        int[] result = new int[array.length-k+1];

        PriorityQueue<Entry> pq = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            pq.add(new Entry(i, array[i]));
        }

        int index = 0;
        result[index++] = pq.peek().val;

        for (int i = k; i < array.length; i++) {
            while (!pq.isEmpty() && pq.peek().index <= i-k) {
                pq.remove();
            }

            pq.add(new Entry(i, array[i]));

            // ith index being considered
            result[index++] = pq.peek().val;
        }
        return result;
    }

    private int[] calcMaxNumber2(int[] array, int k) {
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

    public static void main(String[] args) {
        {
            int[] nums = new int[] { 1, 3, -1, -3, 5, 3, 6, 7 };
            System.out.println(Arrays.toString(new SlidingWindowMaximum().maxSlidingWindow(nums, 3)));
        }

        {
            int[] nums = new int[] { 1 };
            System.out.println(Arrays.toString(new SlidingWindowMaximum().maxSlidingWindow(nums, 1)));
        }

        {
            int[] nums = new int[] { 1,-1};
            System.out.println(Arrays.toString(new SlidingWindowMaximum().maxSlidingWindow(nums, 1)));
        }

        {
            int[] nums = new int[] { 9,11 };
            System.out.println(Arrays.toString(new SlidingWindowMaximum().maxSlidingWindow(nums, 2)));
        }
    }
}
