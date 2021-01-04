package xxx.yyy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NumbersSumToBelow {
    /**
     * Find pair of numbers with index iter1, iter2 in the array such that:
     * array[iter1] + array[iter2] + array[index] = 0
     *
     * @param array: sorted array
     * @param index
     */
    private static void getEntryPairsThatSumBelow(final int[] array, final int index, final int sum) {
        /*
         * Negate array[index] because, we want the sum to be 0
         */
        if (array[index] >= sum) {
            return;
        }

        final int pairSum = sum - array[index];

        /*
         * beginIter goes from begin to end
         * endIter goes from end to begin
         */
        int beginIter = 0;
        int endIter = array.length-1;

        while (beginIter < endIter) {
            if (beginIter == index) {
                // skip index
                beginIter++;
                continue;
            }

            if (endIter == index) {
                // skip index
                endIter--;
                continue;
            }

            if (array[beginIter] + array[endIter] < pairSum) {
                System.out.println(array[beginIter] + "  " + array[endIter] + "  " + array[index]);
                beginIter++;
            } else if (array[beginIter] + array[endIter] > pairSum) {
                endIter--;
            } else {
                endIter--;
            }
        }
    }

    /**
     * Call the above method for every element of the array
     * Time complexity : O(N^2)
     *
     * Plus, since we need a sorted array, time complexity of sorting the array
     * with QuickSort is O(NLogN)
     *
     * @param array: Sorted array
     */
    static void generateSetsOfThreeNumbersThatSumToZero(final int[] array) {
        for (int i = 0; i < array.length; i++) {
            //getEntryPairsThatSumTo(array, i);
        }
    }
    public static void main(final String[] args) {
        final Random r = new Random();
        final int[] array = generateRandomNumbers(r, 50);
        System.out.println("Sorted Lists");
        QuickSorter.qsort(r, array, 0, array.length-1);
        Util.printArray(array);
        generateSetsOfThreeNumbersThatSumToZero(array);
    }

    private static int[] generateRandomNumbers(final Random r, final int count) {
        final Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < count) {
            int val = r.nextInt(50);
            val = (numbers.size() % 2 == 0) ? val : -val;
            numbers.add(val);
        }
        final int[] array = new int[numbers.size()];
        int index = 0;
        for (final int i : numbers) {
            array[index++] = i;
        }
        return array;
    }
}
