package net.lc.dp;

import java.util.HashMap;
import java.util.Map;

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

    static class Pair {
        int begin;
        int end;

        public Pair(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair pair = (Pair) o;

            if (begin != pair.begin)
                return false;
            return end == pair.end;
        }

        @Override
        public int hashCode() {
            int result = begin;
            result = 31 * result + end;
            return result;
        }
    }

    private final Map<Integer, Map<Pair, Integer>> map = new HashMap<>();

    public String longestPalindrome(String s) {

        map.clear();

        char[] array = s.toCharArray();
        if (isPalindrome(array)) {
            return s;
        }

        Pair lastPair = null;

        map.put(1, new HashMap<>());
        for (int i = 0; i < array.length; i++) {
            lastPair = new Pair(i, i);
            map.get(1).put(lastPair, 1);
        }

        map.put(2, new HashMap<>());
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] == array[i]) {
                lastPair = new Pair(i-1, i);
                map.get(2).put(lastPair, 2);
            }
        }

        for (int size = 3; size < array.length; size++) {
            map.put(size, new HashMap<>());

            Map<Pair, Integer> paliMap = map.get(size-2);

            if (paliMap.size() == 0) {
                continue;
            }
            for (int beginIndex = 0; beginIndex < array.length-size+1; beginIndex++) {
                int endIndex = beginIndex+size-1;
                //System.out.println("beginIndex: " + beginIndex + " endIndex: " + endIndex);

                if ((array[beginIndex] == array[endIndex]) && paliMap.containsKey(new Pair(beginIndex+1, endIndex-1))) {
                    lastPair = new Pair(beginIndex, endIndex);
                    map.get(size).put(lastPair, size);
                }
            }
        }

        if (lastPair != null) {
            return s.substring(lastPair.begin, lastPair.end+1);
        }
        return null;
    }
}
