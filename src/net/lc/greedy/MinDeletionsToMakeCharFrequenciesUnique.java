package net.lc.greedy;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/minimum-deletions-to-make-character-frequencies-unique/
 * 1647
 * Greedy
 */
public class MinDeletionsToMakeCharFrequenciesUnique {
    public int minDeletions(String s) {

        int countArray[] = new int[26];
        char[] sarray = s.toCharArray();
        for (char c : sarray) {
            countArray[c - 'a']++;
        }
        //int[] countArray = {0, 0, 4, 5, 7, 7, 8, 8, 8, 9, 10, 10, 14};

        SortedMap<Integer, Integer> map = new TreeMap<>(Comparator.reverseOrder());
        for (int val : countArray) {
            if (val > 0) {
                if (map.containsKey(val)) {
                    map.put(val, 1 + map.get(val));
                } else {
                    map.put(val, 1);
                }
            }
        }

        return removeDups(map);
    }

    private int removeDups(SortedMap<Integer, Integer> map) {

        int deleteCount = 0;
        while (!map.isEmpty()) {
            int key = map.firstKey();

            int val = map.remove(key);
            //System.out.println("key: " + key + "   value: " + val);

            if (val == 1) {
                // only one occurrence of key, nothing can be done
                continue;
            }

            int origKey = key;
            int toDel = 0;
            while (val > 1) {
                val--;
                key--;

                toDel++;
                deleteCount += Math.min(toDel, origKey);

                if (key <= 0) continue;

                if (map.containsKey(key)) {
                    map.replace(key, 1 + map.get(key));
                } else {
                    map.put(key, 1);
                }
            }
        }
        return deleteCount;
    }

    public static void main(String[] args) {

        //System.out.println(new MinDeletionsToMakeCharFrequenciesUnique().minDeletions("abc"));
        //System.out.println(new MinDeletionsToMakeCharFrequenciesUnique().minDeletions("aab"));
        //System.out.println(new MinDeletionsToMakeCharFrequenciesUnique().minDeletions("aaabbbcc"));
        System.out.println(new MinDeletionsToMakeCharFrequenciesUnique().minDeletions("ceabaacb"));

        //System.out.println(new MinDeletionsToMakeCharFrequenciesUnique().minDeletions("abcabc"));
    }
}
