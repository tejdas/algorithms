package net.lc;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis {
    private final List<String> result = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        result.clear();

        char[] array = new char[2 * n];
        constructLegalBraces(n, array, 0, 0, 0);
        return result;
    }

    private void constructLegalBraces(final int n, final char[] array, final int pos, final int lCount, final int rCount) {
        if (pos == array.length) {
            result.add(new String(array));
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
}
