package algo.recurse;

import java.util.ArrayList;
import java.util.List;

public class LegalBraces {
    private static int count = 0;
    private static final List<String> allCombs = new ArrayList<>();

    /**
     * Recursively constructs legal braces.
     * @param n number of matching ( and )
     * @param array
     * @param pos current position that needs to be filled with a ( or )
     * @param lCount Number of ( braces so far.
     * @param rCount Number of ) braces so far.
     */
    private static void constructLegalBraces(final int n, final char[] array, final int pos, final int lCount, final int rCount) {
        if (pos == array.length) {
            final String comb = new String(array);
            System.out.println(comb);
            allCombs.add(comb);
            count++;
            return;
        }

        /*
         * count of '( is still below n. Put a '(' and recurse.
         */
        if (lCount < n) {
            array[pos] = '(';
            constructLegalBraces(n, array, pos + 1, lCount + 1, rCount);
        }

        /*
         * Number of ')' is less than number of '('. Put a ')' and recurse.
         */
        if (rCount < lCount) {
            array[pos] = ')';
            constructLegalBraces(n, array, pos + 1, lCount, rCount + 1);
        }
    }

    private static void constructPathsXY(final int n, final char[] array, final int pos, final int xCount, final int yCount) {
        if (pos == array.length) {
            final String comb = new String(array);
            System.out.println(comb);
            allCombs.add(comb);
            count++;
            return;
        }

        if (xCount < n) {
            array[pos] = 'X';
            constructPathsXY(n, array, pos + 1, xCount + 1, yCount);
        }

        if (yCount < n) {
            array[pos] = 'Y';
            constructPathsXY(n, array, pos + 1, xCount, yCount + 1);
        }
    }

    public static void main1(final String[] args) {
        final int length = 5;
        final char[] array = new char[length*2];
        constructPathsXY(length, array, 0, 0, 0);
        System.out.println("count: " + count);
    }

    public static void main(final String[] args) {
        final long beginTime = System.currentTimeMillis();
        final int length = 4;
        final char[] array = new char[length*2];
        constructLegalBraces(length, array, 0, 0, 0);
        System.out.println("count: " + count);

        final long endTime = System.currentTimeMillis();

        System.out.println("total time taken (millisecs): " + (endTime-beginTime));
    }
}
