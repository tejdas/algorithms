package net.lc;

/**
 * 33
 */
public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return binarySearch(nums, 0, nums.length-1, target);
    }

    private static int binarySearch(int[] array, int left, int right, int searchItem) {
        while (left <= right) {
            if (left == right) {
                if (searchItem == array[left]) {
                    return left;
                } else {
                    return -1;
                }
            }

            if (right == left + 1) {
                if (searchItem == array[left]) {
                    return left;
                } else if (searchItem == array[right]) {
                    return right;
                } else {
                    return -1;
                }
            }

            int median = left + (right - left ) / 2;
            if (searchItem == array[median]) {
                return median;
            }

            if (array[left] < array[median]) {
                if (searchItem >= array[left] && searchItem <= array[median]) {
                    right = median;
                } else {
                    left = median+1;
                }
            } else {
                if (searchItem >= array[median] && searchItem <= array[right]) {
                    left = median;
                } else {
                    right = median-1;
                }
            }
        }
        return -1;
    }
}
