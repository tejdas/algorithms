package net.lc.dp;

/**
 * Dynamic Programming
 * 5
 */
public class LongestPalindromicSubstring {
    static boolean isPalindrome(char[] array) {
        int i = 0;
        int j = array.length - 1;

        while (i < j) {
            if (array[i] != array[j]) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public String longestPalindrome(String s) {
        char[] array = s.toCharArray();
        if (isPalindrome(array)) {
            return s;
        }

        int[] lastPair = null;
        boolean[][] memo = new boolean[s.length()][s.length()];

        for (int i = 0; i < array.length; i++) {
            memo[i][i] = true;
            lastPair = new int[] {i,i};
        }

        for (int i = 1; i < array.length; i++) {
            if (array[i-1] == array[i]) {
                memo[i-1][i] = true;
                lastPair = new int[] {i-1,i};
            }
        }

        for (int size = 3; size < array.length; size++) {
            for (int beginIndex = 0; beginIndex < array.length-size+1; beginIndex++) {
                int endIndex = beginIndex+size-1;


                if ((array[beginIndex] == array[endIndex]) && memo[beginIndex+1][endIndex-1]) {
                    memo[beginIndex][endIndex] = true;
                    lastPair = new int[] {beginIndex, endIndex};
                }
            }
        }

        if (lastPair != null) {
            return s.substring(lastPair[0], lastPair[1]+1);
        }
        return null;
    }
}
