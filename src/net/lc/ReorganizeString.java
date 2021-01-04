package net.lc;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/reorganize-string/submissions/
 * Greedy
 */
public class ReorganizeString {

    static class Pair implements Comparable<Pair> {
        char c;
        int count;

        public Pair(char c, int count) {
            this.c = c;
            this.count = count;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(o.count, this.count);
        }
    }

    public String reorganizeString(String S) {
        if (S == null) return null;
        if (S.length() <= 1) return S;
        Map<Character, Integer> map = new HashMap<>();
        char[] array = S.toCharArray();
        for (char c : array) {
            if (map.containsKey(c)) {
                map.put(c, 1 + map.get(c));
            } else {
                map.put(c, 1);
            }
        }

        /**
         * Construct the string so that, always pick the next character with highest frequency.
         */
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            pq.add(new Pair(entry.getKey(), entry.getValue()));
        }

        StringBuilder sb = new StringBuilder();
        Pair nextToAdd = pq.remove();
        while (!pq.isEmpty()) {
            Pair current = nextToAdd;
            sb.append(current.c);
            current.count--;
            /**
             * Do not add current character immediately back to the PQ. If it still had the highest
             * frequency, it will be again picked up right away, resulting in consecutive occurrences,
             * which is not what we want.
             *
             * Therefore, first remove the next highest-occurring character, and then, put the current
             * one back in PQ.
             */
            nextToAdd = pq.remove();
            if (current.count > 0) pq.add(current);
        }

        if (nextToAdd.count == 1) {
            sb.append(nextToAdd.c);
            return sb.toString();
        } else {
            // the
            return "";
        }
    }

    public static void main(String[] args) {
        {
            String s = "aab";
            System.out.println(new ReorganizeString().reorganizeString(s));
        }

        {
            String s = "aaab";
            System.out.println(new ReorganizeString().reorganizeString(s));
        }
    }
}
