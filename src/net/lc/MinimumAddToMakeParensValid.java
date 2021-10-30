package net.lc;

/**
 * 921
 */
public class MinimumAddToMakeParensValid {
    public int minAddToMakeValid(String input) {
        if (input == null || input.length() == 0) return 0;
        char[] array = input.toCharArray();

        int unmatchedParen = 0;
        int count = 0;
        for (char c : array) {
            if (c == '(') {
                unmatchedParen++;
            } else {
                if (unmatchedParen > 0) {
                    unmatchedParen--;
                } else {
                    // missing corresponding '('
                    count++;
                }
            }
        }
        return count + unmatchedParen;
    }
}
