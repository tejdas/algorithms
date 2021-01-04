package xxx.yyy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PalindromeSubsequenceDriver {
    public static void main(final String[] args) {
        //displayPalindromes("abqwerba");
        //displayPalindromes("aab");
        displayPalindromes("malayalam");
        displayPalindromeLength("alayalam");
        displayPalindromeLength("abqwerba");
    }

    /*
     * aba, bqb, e, abwba, b, awa, a, abeba, bwb, abqba, w, aqa, aea, r, abrba, q, beb, ara, brb, abba, aa, bb
     */

    static class Pair {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + beginIndex;
            result = prime * result + endIndex;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Pair other = (Pair) obj;
            if (beginIndex != other.beginIndex)
                return false;
            if (endIndex != other.endIndex)
                return false;
            return true;
        }

        public int beginIndex;
        public int endIndex;

        public Pair(final int beg, final int end) {
            this.beginIndex = beg;
            this.endIndex = end;
        }
    }

    private static Map<Pair, Set<String>> palindromes = new HashMap<>();

    private static Map<Pair, Integer> palindromesLength = new HashMap<>();

    public static void displayPalindromes(final String str) {
        palindromes.clear();
        final char[] array = str.toCharArray();

        final Set<String> palindromes = generatePalindromes(array, 0, array.length-1);
        if (palindromes != null && !palindromes.isEmpty()) {
            for (final String s : palindromes) {
                System.out.print(s + "  ");
            }
            System.out.println();
        }
    }

    public static void displayPalindromeLength(final String str) {
        palindromesLength.clear();
        final char[] array = str.toCharArray();

        final int maxPalindromeLength = getMaxPalindromeLength(array, 0, array.length - 1);
        System.out.println("Max palindrome length: " + maxPalindromeLength);
    }

    public static Set<String> generatePalindromes(final char[] array, final int beginIndex, final int endIndex) {

        if (beginIndex < 0 || endIndex < 0) {
            return null;
        }

        if (beginIndex > endIndex) {
            return null;
        }

        final Pair p = new Pair(beginIndex, endIndex);
        if (palindromes.containsKey(p)) {
            return palindromes.get(p);
        }

        if (beginIndex == endIndex) {
            final Set<String> val = Collections.singleton(String.valueOf(array[beginIndex]));
            palindromes.put(p, val);
            return val;
        }

        final Set<String> output = new HashSet<>();
        palindromes.put(p,  output);
        if (array[beginIndex] == array[endIndex]) {

            final Set<String> pals = generatePalindromes(array, beginIndex + 1, endIndex - 1);
            if (pals != null && !pals.isEmpty()) {
                output.addAll(pals);
                for (final String s : pals) {
                    output.add(String.format("%c%s%c", array[beginIndex], s, array[endIndex]));
                }
            }
            output.add(String.format("%c%c", array[beginIndex], array[endIndex]));
        }

        final Set<String> palsLeft = generatePalindromes(array, beginIndex, endIndex-1);
        if (palsLeft != null && !palsLeft.isEmpty()) {
            output.addAll(palsLeft);
        }

        final Set<String> palsRight = generatePalindromes(array, beginIndex+1, endIndex);
        if (palsRight != null && !palsRight.isEmpty()) {
            output.addAll(palsRight);
        }
        return output;
    }

    public static int getMaxPalindromeLength(final char[] array, final int beginIndex, final int endIndex) {

        if (beginIndex < 0 || endIndex < 0) {
            return 0;
        }

        if (beginIndex > endIndex) {
            return 0;
        }

        final Pair p = new Pair(beginIndex, endIndex);
        if (palindromesLength.containsKey(p)) {
            return palindromesLength.get(p);
        }

        if (beginIndex == endIndex) {
            palindromesLength.put(p, 1);
            return 1;
        }

        int length = 0;
        if (array[beginIndex] == array[endIndex]) {
            length = 2 + getMaxPalindromeLength(array, beginIndex + 1, endIndex - 1);
        }

        final int lengthLeft = getMaxPalindromeLength(array, beginIndex, endIndex - 1);

        final int lengthRight = getMaxPalindromeLength(array, beginIndex + 1, endIndex);

        length = Math.max(length, Math.max(lengthLeft, lengthRight));
        palindromesLength.put(p, length);
        return length;
    }
}
