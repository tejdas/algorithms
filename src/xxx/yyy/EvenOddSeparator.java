package xxx.yyy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EvenOddSeparator {
    private static void swap(final Integer[] array, final int pos1, final int pos2) {
        final int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static void swap(final int[] array, final int pos1, final int pos2) {
        final int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static void printArray(final Integer[] array) {
        for (final int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void separateEvenOdd(final Integer[] array) {
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            if ((array[i]%2 == 1) && (array[j]%2 == 0)) {
                swap(array, i, j);
                i++;
                j--;
            } else {
                if (array[i]%2 == 0) {
                    i++;
                }
                if (array[j]%2 == 1) {
                    j--;
                }
            }
        }
    }

    static void separate012(final int[] array) {
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

    public static void main0(final String[] args) {
        final Random r = new Random();
        final Set<Integer> input = new HashSet<>();
        while (input.size() < 50) {
            input.add(r.nextInt(100));
        }
        final Integer[] inputArray = input.toArray(new Integer[input.size()]);
        //Integer[] inputArray = {13, 17, 21, 15, 19, 4};
        printArray(inputArray);
        separateEvenOdd(inputArray);
        printArray(inputArray);
    }

    public static void main1(final String[] args) {

        final int[] array = new int[32];
        final Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(3);
        }
        System.out.println(Arrays.toString(array));
        //final int[] array = {0, 2, 1, 1, 2, 0, 1, 2, 1, 0};
        //final int[] array = {2, 1, 2, 1};
        separate012(array);
        System.out.println(Arrays.toString(array));
    }

    public static void main(final String[] args) {

        final int[] array = {0, 1, 1, 2, 1, 2, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 2, 2, 1, 1, 2, 2, 0, 2, 2, 1, 1, 0, 1};
        //final int[] array = {0, 2, 2, 0, 0, 2, 0, 2, 0, 2, 0};
        System.out.println(Arrays.toString(array));
        separate012(array);
        System.out.println(Arrays.toString(array));
    }
}
