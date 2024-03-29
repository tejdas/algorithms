package net.lc.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 670
 * Greedy
 * Array
 */
public class MaximumSwap {
    public int maximumSwap(int num) {
        if (num <= 10) return num;
        Integer[] orig = toArray(num);

        Integer[] sorted = new Integer[orig.length];

        System.arraycopy(orig, 0, sorted, 0, orig.length);

        Arrays.sort(sorted, Collections.reverseOrder());

        /**
         * Tokenize the number into an array: orig
         * Sort the array (max first):  sorted
         * sorted is the absolute max we can get
         * if orig == sorted, nothing to be done
         * Else, scan right from pos 0 until we get an index
         * where the value is not where it needs to be (in sorted array).
         * Try to swap the element with the one
         */

        int index;
        for (index = 0; index < orig.length-1; index++) {
            if (orig[index] != sorted[index]) {
                break;
            }
        }

        if (index < orig.length-1) {
            int j;
            for (j = orig.length-1; j > index; j--) {
                if (orig[j] == sorted[index]) {
                    break;
                }
            }

            swap(orig, index, j);
            return toNum(orig);
        }

        return num;
    }

    static void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static Integer[] toArray(int num) {
        List<Integer> list = new ArrayList<>();

        while (num > 0) {
            int remainder = num % 10;
            list.add(remainder);
            num = num / 10;
        }

        int size = list.size();
        Integer[] array = new Integer[size];
        for (int index = 0; index < size; index++) {
            array[index] = list.get(size-index-1);
        }
        return array;
    }

    static int toNum(Integer[] array) {
        int sum = array[0];

        for (int index = 1; index < array.length; index++) {
            sum = sum * 10 + array[index];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new MaximumSwap().maximumSwap(2736));
    }
}
