package net.lc.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 115
 * Dynamic Programming
 * two-dimensional memo array
 */
public class DistinctSubsequences {
    public int numDistinct(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) return 0;
        if (s.equals(t)) return 1;
        char[] sarray = s.toCharArray();
        char[] tarray = t.toCharArray();

        /**
         * Two-dimensional DP
         * memo[is][jt] = number of distinct subsequences of t(0:jt) in s(0:is)
         */
        int[][] memo = new int[sarray.length][tarray.length];

        /**
         * For t, if a char is repeating, keep track of each char's positions in a list.
         */
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < tarray.length; i++) {
            char c = tarray[i];
            List<Integer> list = map.computeIfAbsent(c, k -> new ArrayList<>());
            list.add(i);
        }

        if (sarray[0] == tarray[0]) memo[0][0] = 1;

        /**
         * if sarray[i] == tarray[j], then
         * memo[i][j] = SUM memo[k][j-1] for all k between 0 and i-1
         */

        for (int i = 1; i < sarray.length; i++) {
            char c = sarray[i];

            if (!map.containsKey(c)) continue;

            List<Integer> indices = map.get(c);
            for (int idx : indices) {
                if (idx == 0) {
                    memo[i][idx] = 1;
                } else {
                    int sum = 0;
                    for (int j = i - 1; j >= 0; j--) {
                        sum += memo[j][idx - 1];
                    }
                    memo[i][idx] = sum;
                }
            }
        }

        /**
         * A subsequence of t cannot be found in a substring of s of length < tarray.length
         * Therefore, start from tarray.length
         */
        int res = 0;
        for (int i = tarray.length-1; i < sarray.length; i++) {
            res += memo[i][tarray.length-1];
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new DistinctSubsequences().numDistinct("babgbag", "bag"));
        System.out.println(new DistinctSubsequences().numDistinct("rabbbit", "rabbit"));
        System.out.println(new DistinctSubsequences().numDistinct("ccc", "c"));
    }
}
