package xxx.yyy;

public class SortedRotatedArray {
    /**
     * BinarySearch of an item in a sorted array that is rotated.
     * @param array
     * @param first
     * @param last
     * @param searchItem
     * @return
     */
    public static int binarySearch(final int[] array, final int first, final int last,
            final int searchItem) {
        if (first == last) {
            if (searchItem == array[first]) {
                return first;
            } else {
                return -1;
            }
        }

        if (last == first + 1) {
            if (searchItem == array[first]) {
                return first;
            } else if (searchItem == array[last]) {
                return last;
            } else {
                return -1;
            }
        }
        final int median = first + (last - first ) / 2;

        if (searchItem == array[median]) {
            return median;
        }

        if (array[first] < array[median]) {
            /*
             * First half is a sorted array (meaning second half is where it
             * rotates). If the searchItem is in the first half, then
             * recursively search for it, else recursively search in second
             * half.
             */
            if (searchItem >= array[first] && searchItem <= array[median]) {
                return binarySearch(array, first, median, searchItem);
            } else {
                return binarySearch(array, median + 1, last, searchItem);
            }
        } else {
            /*
             * Second half is a sorted array (meaning first half is where it
             * rotates). If the searchItem is in the second half, then
             * recursively search for it, else recursively search in first half.
             */
            if (searchItem >= array[median] && searchItem <= array[last]) {
                return binarySearch(array, median, last, searchItem);
            } else {
                return binarySearch(array, first, median - 1, searchItem);
            }
        }
    }

    /**
     * Find the smallest value in a sorted/rotated array.
     * @param array
     * @param first
     * @param last
     * @return
     */
    public static int findSmallest(final int[] array, final int first, final int last) {
        if (first == last)
            return array[first];
        if (array[first] <= array[last])
            return array[first];
        if (last == first + 1) {
            return (array[first] < array[last]) ? array[first] : array[last];
        }
        final int median = (first + last) / 2;
        if (array[first] > array[median]) {
            return findSmallest(array, first, median);
        } else {
            return findSmallest(array, median, last);
        }
    }

    public static void main(final String[] args) {
        final int[] array = { 7, 8, 9, 10, 14, 15, 16, 19, 20, 25, 1, 3, 4, 5, 6 };
        for (final int item : array) {
            System.out.println("searching item: " + item);
            final int pos = binarySearch(array, 0, array.length - 1, item);
            System.out.println(pos + "  ------------------");
        }

        final int min = findSmallest(array, 0, array.length - 1);
        System.out.println("Get minimum: " + min);

        int[] arr2 = {3, 4, 5, 1, 2};
        System.out.println("Get minimum: " + findSmallest(arr2, 0, arr2.length-1));
    }
}
