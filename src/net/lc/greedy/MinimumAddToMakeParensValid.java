package net.lc.greedy;

/**
 * 921
 * Greedy
 */
public class MinimumAddToMakeParensValid {
    public int minAddToMakeValid(String input) {
        if (input == null || input.length() == 0) return 0;
        char[] array = input.toCharArray();

        int unmatchedLeftParen = 0;
        int unmatchedRightParen = 0;
        for (char c : array) {
            if (c == '(') {
                unmatchedLeftParen++;
            } else {
                if (unmatchedLeftParen > 0) {
                    unmatchedLeftParen--;
                } else {
                    // missing corresponding '('
                    unmatchedRightParen++;
                }
            }
        }
        /**
         * We came out of the loop with '(' unmatchedLeftParen, that have no ')'
         * Also, for unmatchedRightParen ')', we did not have '(' before it for matching.
         */
        return unmatchedRightParen + unmatchedLeftParen;
    }
}
