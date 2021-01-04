package net.lc;

/**
 * https://leetcode.com/problems/sort-colors/
 * Two-pointer
 */
public class SortColors {
    public void sortColors(int[] array) {
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            if ((array[i] == 2) && (array[j] == 0 || array[j] == 1)) {
                swap(array, i, j);
                i++;
                j--;
            } else {
                if (array[i] == 0 || array[i] == 1) {
                    i++;
                }
                if (array[j] == 2) {
                    j--;
                }
            }
        }

        while (j >= 0 && array[j] == 2) {
            j--;
        }
        i = 0;

        while (i < j) {
            if (array[i] == 1 && array[j] == 0) {
                swap(array, i, j);
                i++;
                j--;
            } else {
                if (array[i] == 0) {
                    i++;
                }
                if (array[j] == 1) {
                    j--;
                }
            }
        }
    }

    private static void swap(final int[] array, final int pos1, final int pos2) {
        final int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
}
