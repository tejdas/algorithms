package xxx.yyy;

import java.util.Arrays;
import java.util.Random;

public class QuickSorter {
    private static void swap(final int[] array, final int posx, final int posy) {
        final int temp = array[posx];
        array[posx] = array[posy];
        array[posy] = temp;
    }

    private static int getPartitionIndex(final Random random, final int[] array, final int x,
            final int y) {
        final int index = x + random.nextInt(y - x + 1);
        final int val = array[index];

        int i = x;
        int j = y;
        while (i < j) {
            while (array[i] < val)
                i++;
            while (array[j] > val)
                j--;
            if (i < j) {
                swap(array, i, j);
            }
        }
        return i;
    }

    static void qsort(final Random r, final int[] array, final int x, final int y) {
        if (x == y) {
            return;
        }
        if (x == y - 1) {
            if (array[x] > array[y]) {
                swap(array, x, y);
            }
            return;
        }
        final int index = getPartitionIndex(r, array, x, y);
        if (index > x) {
            qsort(r, array, x, index - 1);
        }
        if (index < y) {
            qsort(r, array, index + 1, y);
        }
    }

    static int findMedian(final Random r, final int[] array, final int x, final int y) {
        if (x == y) {
            return array[x];
        }
        final int index = getPartitionIndex(r, array, x, y);
        if (index == array.length / 2) {
            return array[index];
        } else if (index < array.length / 2) {
            return findMedian(r, array, index + 1, y);
        } else {
            return findMedian(r, array, x, index - 1);
        }
    }

    static int findKthMinimum(final Random r, final int[] array, final int x, final int y, final int k) {
        if (x == y) {
            return array[x];
        }

        final int index = getPartitionIndex(r, array, x, y);
        if (k == index) {
            return array[index];
        } else if (k > index) {
            return findKthMinimum(r, array, index + 1, y, k);
        } else {
            return findKthMinimum(r, array, x, index - 1, k);
        }
    }

    public static void main1(final String[] args) {
        final Random r = new Random();
        final int len = 40;
        final int[] array = Util.generateRandomNumbers(r, len);
        final int[] arrayCP = new int[len];
        System.arraycopy(array, 0, arrayCP, 0, len);

        final int val = findMedian(new Random(), array, 0, array.length - 1);
        System.out.println("median: " + val);
        Util.printArray(array);
        System.out.println();
        qsort(new Random(), arrayCP, 0, arrayCP.length - 1);
        Util.printArray(arrayCP);
        final int medianInSortedArray = arrayCP[len / 2];
        System.out.println("median in sorted array: " + medianInSortedArray);
        // assertTrue(val, medianInSortedArray);
    }

    public static void main(final String[] args) {
        final Random r = new Random();
        final int len = 20;
        final int[] array = //new int[] {81,  4,  54,  179,  128,  131,  95,  192,  180,  61};
                Util.generateRandomNumbers(r, len);
        System.out.println(Arrays.toString(array));

        final int[] arrayCP = new int[len];
        System.arraycopy(array, 0, arrayCP, 0, len);

        final int val = findKthMinimum(r, array, 0, array.length-1, 5);
        System.out.println("kth min: " + val);

        qsort(new Random(), arrayCP, 0, arrayCP.length - 1);
        Util.printArray(arrayCP);

    }
}
