package net.lc;

/**
 * 209
 * Two-pointer
 */
public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] array) {
        if (array == null || array.length == 0)
            return 0;

        int minSize = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0;
        int right = -1;

        for (int index = 0; index < array.length; index++) {
            sum += array[index];
            right++;

            if (sum < s)
                continue;

            do {
                sum -= array[left];
                left++;
            } while (sum >= s);

            left--;
            sum += array[left];

            minSize = Math.min(minSize, (right - left + 1));
        }

        return minSize == Integer.MAX_VALUE? 0 : minSize;
    }
}
