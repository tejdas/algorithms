package xxx.yyy;

import java.util.Arrays;
import java.util.Random;

public class ZerosAndOnes {

    private int findFirseOne(int[] array) {
        int left = 0;
        int right = array.length - 1;

        if (array[right] == 0)
            return -1;

        if (array[left] == 1)
            return 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == 0) {
                if (array[mid + 1] == 1)
                    return mid + 1;
                else {
                    left = mid + 1;
                }
            } else {
                if (array[mid - 1] == 0)
                    return mid;
                else {
                    right = mid - 1;
                }
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = buildArray();
        System.out.println(Arrays.toString(array));
        int idx = new ZerosAndOnes().findFirseOne(array);
        System.out.println(idx);

        System.out.println(new ZerosAndOnes().findFirseOne(new int[] { 1, 1 }));
    }

    private static int[] buildArray() {
        Random r = new Random();

        int numZeros = r.nextInt(30);
        int numOnes = r.nextInt(30);
        System.out.println("numZeros: " + numZeros + "  numOnes: " + numOnes);

        int[] array = new int[numOnes + numZeros];

        for (int i = 0; i < numZeros; i++) {
            array[i] = 0;
        }

        for (int i = numZeros; i < array.length; i++) {
            array[i] = 1;
        }
        return array;
    }
}
