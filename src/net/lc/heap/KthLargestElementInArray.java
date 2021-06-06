package net.lc.heap;

/**
 * 215
 * QuickSort
 * Could be done using a PriorityQueue
 */
public class KthLargestElementInArray {
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) return -1;
        return findKthMinimum(nums, 0, nums.length-1, nums.length-k);
    }

    static int findKthMinimum(final int[] array, final int x, final int y, final int k) {
        if (x == y) {
            return array[x];
        }

        final int index = getPartitionIndex(array, x, y);
        if (k == index) {
            return array[index];
        } else if (k > index) {
            return findKthMinimum(array, index + 1, y, k);
        } else {
            return findKthMinimum(array, x, index - 1, k);
        }
    }

    private static int getPartitionIndex(final int[] array, final int x, final int y) {
        final int val = array[y];

        int i = x-1;
        for (int j = x; j < y; j++) {
            if (array[j] <= val) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i+1, y);
        return (i+1);
    }

    private static void swap(final int[] array, final int posx, final int posy) {
        final int temp = array[posx];
        array[posx] = array[posy];
        array[posy] = temp;
    }
}
