package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrayPartitionMinimumDifference {

    static void partition(final int[] array, final List<Integer> output1, final List<Integer> output2) {
        Arrays.sort(array);

        Util.printArray(array);

        int endIndex;
        if (array.length %2 == 0) {
            endIndex = 0;
        } else {
            endIndex = 1;
            output2.add(array[0]);
        }

        List<Integer> a = output1;
        List<Integer> b = output2;
        for (int index = array.length-1; index > endIndex; index-=2) {
            a.add(array[index]);
            b.add(array[index-1]);

            final List<Integer> temp = a;
            a = b;
            b = temp;
        }
    }

    public static void main(final String[] args) {
        final Random r = new Random();
        //final int[] array = Util.generateRandomNumbers(r, 11);
        final int[] array = {1, 5, 11, 5};

        final List<Integer> output1 = new ArrayList<>();
        final List<Integer> output2 = new ArrayList<>();

        partition(array, output1, output2);

        for (final int i : output1) {
            System.out.print(i + "  ");
        }
        System.out.println();

        for (final int i : output2) {
            System.out.print(i + "  ");
        }
        System.out.println();

    }

}
