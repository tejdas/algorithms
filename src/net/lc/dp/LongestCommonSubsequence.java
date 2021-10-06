package net.lc.dp;

public class LongestCommonSubsequence {
    int[][] memo =  null;
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.compareTo(text2) == 0) return text1.length();

        memo = new int[text1.length()+1][text2.length()+1];

        char[] a1 = text1.toCharArray();
        char[] a2 = text2.toCharArray();

        for (int i = 1; i <= a1.length; i++)  {
            for (int j = 1; j <= a2.length; j++)  {

                int v1 = memo[i-1][j];
                 v1 = Math.max(memo[i][j-1], v1);

                if (a1[i-1] == a2[j-1]) {
                    v1 = Math.max(v1,  1 + memo[i-1][j-1]);
                }
                memo[i][j] = v1;
            }
        }

        return  memo[a1.length][a2.length];
    }

    private int longestCommonSubseq(char[] text1, char[] text2, int index1, int index2) {
        if (index1 < 0 || index2 < 0) return 0;

        if (memo[index1][index2] != -1) return memo[index1][index2];

        int res = -1;
        if (text1[index1] == text2[index2]) {
            res = Math.max(res,  1 +  longestCommonSubseq(text1, text2, index1-1, index2-1));
        }

        res = Math.max(res, longestCommonSubseq(text1, text2, index1, index2-1));
        res = Math.max(res, longestCommonSubseq(text1, text2, index1-1, index2));

        memo[index1][index2] =  res;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence("abcde", "afgcdneo"));
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence("abcde", "fghi"));
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence("bl", "yby"));
    }
}
