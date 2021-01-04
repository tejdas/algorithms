package xxx.yyy;

import java.util.Arrays;
import java.util.Random;

public class SortedArray {
    static int findNearest(final int[] array, final int start, final int end, final int val) {
        if (array.length == 0) return -1;
        if (val <= array[start]) return array[start];
        if (val >= array[end]) return array[end];
        if (end == start+1) {
            if ((val-array[start]) < (array[end]-val))
                return array[start];
            else
                return array[end];
        }
        final int median = (start+end)/2;
        if (val == array[median]) return val;
        else if (val < array[median])
            return findNearest(array, start, median, val);
        else {
            return findNearest(array, median, end, val);
        }
    }

    static int findFirstOccuranceGreaterThan(final int[] array, final int start, final int end, final int val) {
        if (array == null || array.length == 0) return -1;
        if (start > end) {
            return -1;
        }

        if (start == end) {
            if (array[start] > val) {
                return array[start];
            } else {
                return -1;
            }
        }

        if (end == start+1) {
            if (val < array[start])
                return array[start];
            else if (val < array[end])
                return array[end];
            else
                return -1;
        }

        final int median = (start+end)/2;

        if (val > array[median]) {
            return findFirstOccuranceGreaterThan(array, median, end, val);
        } else if (val == array[median]) {
            return array[median+1];
        } else if (val > array[median-1]) {
            return array[median];
        } else {
            return findFirstOccuranceGreaterThan(array, start, median, val);
        }
    }

    public static void main1(final String[] args) {
        final Random r = new Random();
        final int[] array = Util.generateRandomNumbers(r,  20);
        Arrays.sort(array);
        Util.printArray(array);

        final int val = r.nextInt(200);
        System.out.println(val);
        System.out.println(findNearest(array, 0, array.length-1, val));
    }

    public static void main2(final String[] args) {
        final Random r = new Random();
        //final int[] array = Util.generateRandomNumbers(r,  20);
        final int[] array = {7, 8, 27, 28, 36, 50, 79, 94, 102, 104, 109, 111, 114, 116, 144, 148, 166, 173, 188, 192};
        Arrays.sort(array);
        Util.printArray(array);

        final int val = r.nextInt(200);
        System.out.println(val);
        System.out.println(findFirstOccuranceGreaterThan(array, 0, array.length-1, val));
    }

    public static void main(final String[] args) {
        new Random();
        //final int[] array = Util.generateRandomNumbers(r,  20);
        final int[] array = {7};
        Arrays.sort(array);
        Util.printArray(array);

        final int val = 7;
        System.out.println(val);
        System.out.println(findFirstOccuranceGreaterThan(array, 0, array.length-1, val));
    }
}
