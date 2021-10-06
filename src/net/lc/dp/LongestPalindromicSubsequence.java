package net.lc.dp;

/**
 * 516
 * DP
 */
public class LongestPalindromicSubsequence {
    private int[][] matrix;

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

    public int longestPalindromeSubseq(String s) {
        char[] array = s.toCharArray();
        if (isPalindrome(array)) {
            return array.length;
        }

        matrix = new int[s.length()][s.length()];

        for (int size = 1; size <= array.length; size++) {
            for (int startPos = 0; startPos < array.length - size + 1; startPos++) {
                int endPos = startPos + size - 1;

                if (endPos == startPos) {
                    matrix[startPos][endPos] = 1;
                    continue;
                } else if (endPos == startPos+1) {
                    if (array[startPos] == array[endPos]) {
                        matrix[startPos][endPos] = 2;
                    } else {
                        matrix[startPos][endPos] = 1;
                    }
                    continue;
                }

                int l1 = 0;
                if (array[startPos] == array[endPos]) {
                    l1 = 2 + matrix[startPos + 1][endPos - 1];
                }
                int l2 = matrix[startPos + 1][endPos];
                int l3 = matrix[startPos][endPos - 1];

                matrix[startPos][endPos] = Integer.max(l1, Integer.max(l2, l3));
            }
        }
        return matrix[0][array.length - 1];
    }
}

