package net.lc;

/**
 * 153
 */
public class MinimumInRotatedSortedArray {
    public int findMin(int[] array) {
        if (array == null || array.length==0)
            return 0;
        int left = 0;
        int right = array.length-1;

        while (left <= right) {
            if (left == right) return array[left];

            if (array[left] <= array[right]) return array[left];

            if (right == left+1) {
                return Math.min(array[left], array[right]);
            }

            int median = left + (right-left)/2;

            if (array[left] > array[median]) {
                right = median;
            } else {
                left = median;
            }
        }
        return 0;
    }
}
