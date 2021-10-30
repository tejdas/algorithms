package net.lc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NumberOfSquarefulArrays {
    private static int count = 0;
    private static final Set<Integer> squareset = new HashSet<>();
    public int numSquarefulPerms(int[] input) {
        count = 0;
        squareset.clear();
        printPermutations(input, 0);
        return count;
    }

    private static void swap(final int[] array, final int pos1, final int pos2) {
        final int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static void printPermutations(final int[] array, final int pos) {
        if (pos == array.length - 1) {

            //System.out.println(Arrays.toString(array));
            boolean isSquare = true;
            for (int i = 1; i < array.length; i++) {
                if (!isSquare(array[i] + array[i-1])) {
                    isSquare = false;
                    break;
                }
            }
            if (isSquare) {
                count++;
            }
            return;
        }

        final Set<Integer> seen = new HashSet<>();

        for (int iter = pos; iter < array.length; iter++) {
            if (seen.contains(array[iter])) {
                continue;
            }

            seen.add(array[iter]);

            swap(array, pos, iter);
            printPermutations(array, pos + 1);
            swap(array, iter, pos);
        }
    }

    private static boolean isSquare(int num) {
        //System.out.println("isSquare: " + num);
        if (squareset.contains(num)) return true;
        double val = Math.sqrt(num);
        boolean res = (val == Math.floor(val));
        if (res) squareset.add(num);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new NumberOfSquarefulArrays().numSquarefulPerms(new int[] {1,17,8}));
        System.out.println(new NumberOfSquarefulArrays().numSquarefulPerms(new int[] {75,91,39,33,39,39,69,20,43,38,48,29}));

        System.out.println(new NumberOfSquarefulArrays().numSquarefulPerms(new int[] {89,72,71,44,50,72,26,79,33,27,84}));
    }
}
