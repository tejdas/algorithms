package net.lc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/shortest-word-distance-ii/
 */
public class ShortestWordDistanceII {
    private final Map<String, List<Integer>> map = new HashMap<>();
    public ShortestWordDistanceII(String[] words) {
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            List<Integer> l = map.computeIfAbsent(word, k -> new ArrayList<>());
            l.add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        return findClosest(l1, l2);
    }

    private static int findClosest(List<Integer> l1, List<Integer> l2) {

        int closest = Integer.MAX_VALUE;

        int index1 = 0;
        int index2 = 0;

        while (index1 < l1.size() && index2 < l2.size()) {
            int diff = Math.abs(l1.get(index1) - l2.get(index2));
            closest = Math.min(closest, diff);

            if (l1.get(index1) < l2.get(index2)) {
                index1++;
            } else {
                index2++;
            }
        }

        return closest;
    }
}
