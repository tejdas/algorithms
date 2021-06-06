package net.lc.dp;

/**
 * 647
 * DP
 * Bottom-up
 */
public class PalindromicSubstrings {
    public int countSubstrings(String s) {
        if (s == null || s.isEmpty()) return 0;

        char[] array = s.toCharArray();

        boolean[][] memo = new boolean[array.length][array.length];

        int count = 1;
        memo[0][0] = true;

        for (int i = 1; i < array.length; i++) {
            memo[i][i] = true;
            count++;

            if (array[i-1] == array[i]) {
                count++;
                memo[i-1][i] = true;
            }
        }

        for (int size = 3; size <= array.length; size++) {
            for (int begin = 0; begin < array.length-size+1; begin++) {
                int end = begin+size-1;

                if (memo[begin+1][end-1] && array[begin] == array[end]) {
                   memo[begin][end] = true;
                   count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(new PalindromicSubstrings().countSubstrings("malayalam"));
        System.out.println(new PalindromicSubstrings().countSubstrings("aaa"));
    }
}
