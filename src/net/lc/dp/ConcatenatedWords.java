package net.lc.dp;

import java.util.*;

/**
 * 472
 * https://leetcode.com/problems/concatenated-words/
 * Dynamic Programming
 */
public class ConcatenatedWords {
    private final List<String> result = new ArrayList<>();
    private final Set<String> wordSet = new HashSet<>();

    public List<String> findAllConcatenatedWordsInADict(String[] words) {

        result.clear();
        wordSet.clear();

        if (words == null || words.length == 0)
            return result;
        Arrays.sort(words, Comparator.comparingInt(String::length));


        for (String word : words) {
            if (wordBreak(word)) {
                result.add(word);
            }
            wordSet.add(word);
        }

        return result;
    }

    private boolean wordBreak(String s) {
        if (s.isEmpty()) return false;
        char[] array = s.toCharArray();

        /**
         * memoization results
         */
        boolean[] flag = new boolean[array.length+1];

        flag[0] = true;

        for (int i = 1; i <= array.length; i++) {
            for (int j = i-1; j >=0; j--) {
                if (flag[j]) {
                    String sub = s.substring(j, i);
                    if (wordSet.contains(sub)) {
                        flag[i] = true;
                        break;
                    }
                }
            }
        }
        return flag[array.length];
    }
}
