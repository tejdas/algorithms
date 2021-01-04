package xxx.yyy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Util {
    public static void printArray(int[] array) {
        for (final Integer i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printArray(int[] array, int min, int max) {
        for (int i = min; i < max; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void printArray(Integer[] array) {
        for (final Integer i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    public static int[] generateRandomNumbers(Random r, int count) {
        final Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < count) {
            numbers.add(r.nextInt(200));
        }
        final int[] array = new int[numbers.size()];
        int index = 0;
        for (final int i : numbers) {
            array[index++] = i;
            System.out.print(i + "  ");
        }
        System.out.println();
        return array;
    }
}
