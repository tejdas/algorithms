package algo.recurse;

import java.util.Arrays;

/**
 * Place numbers 1 through n twice such that,
 * for any number 1 <= i <= n, there are i
 * numbers in between the two occurrences of i.
 *
 * @author tejdas
 */
public class NumbersPlacer {
    private static int count = 0;
    static void placeNumbers(int[] array, int numberToPlace) {
        for (int i = 0; i < (array.length - numberToPlace - 1); i++) {
            if ((array[i] == 0) && (array[i+numberToPlace+1] == 0)) {
                array[i] = numberToPlace;
                array[i+numberToPlace+1] = numberToPlace;
                if (numberToPlace == 1) {
                    printArray(array);
                    count++;
                } else {
                    placeNumbers(array, numberToPlace - 1);
                }
                array[i] = 0;
                array[i+numberToPlace+1] = 0;
            }
        }
    }

    private static void printArray(int[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + "  ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxNumber = 11;
        int[] array = new int[maxNumber*2];
        Arrays.fill(array, 0);
        placeNumbers(array, maxNumber);
        System.out.println("count: " + count);
    }
}
