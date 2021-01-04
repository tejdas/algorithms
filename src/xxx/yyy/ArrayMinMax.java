package xxx.yyy;

import java.util.Random;

public class ArrayMinMax {

    public static void main(final String[] args) {
        final Random r = new Random();
        final int[] array = Util.generateRandomNumbers(r,  20);
        Util.printArray(array);

        final int max = findMax(array, 0, array.length-1);
        System.out.println(max);
    }

    static int findMax(final int[] array, final int m, final int n) {

        if (m > n) {
            return Integer.MIN_VALUE;
        } else if (m == n) {
            return array[m];
        } else if (n == m+1) {
            return Math.max(array[m],  array[n]);
        }

        final int mid = (m+n)/2;
        final int leftMax = findMax(array, m, mid-1);
        final int rightMax = findMax(array, mid, n);

        return Math.max(leftMax, rightMax);
    }
}
