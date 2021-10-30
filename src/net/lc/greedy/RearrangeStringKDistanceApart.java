package net.lc.greedy;

import java.util.*;

/**
 * https://leetcode.com/problems/rearrange-string-k-distance-apart/submissions/
 * Greedy
 * PriorityQueue
 * 358
 */
public class RearrangeStringKDistanceApart {
    static class Pair implements Comparable<Pair> {
        char c;
        int count;
        int pos;

        public Pair(char c, int count, int pos) {
            this.c = c;
            this.count = count;
            this.pos = pos;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(o.count, this.count);
        }
    }

    public String rearrangeString(String s, int k) {
        if (s == null)
            return null;
        if (s.length() <= 1)
            return s;
        Map<Character, Integer> map = new HashMap<>();
        char[] array = s.toCharArray();
        for (char c : array) {
            if (map.containsKey(c)) {
                map.put(c, 1 + map.get(c));
            } else {
                map.put(c, 1);
            }
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            pq.add(new Pair(entry.getKey(), entry.getValue(), -1));
        }

        StringBuilder sb = new StringBuilder();
        int pos = 0;

        /**
         * Pick the next character from PriorityQueue with the highest remaining frequency.
         *
         * For characters that cannot be immediately put (until k-distance, park in posMap
         * and add back to PQ once they satisfy k-distance).
         */
        Map<Integer, Pair> posMap = new HashMap<>();

        while (!pq.isEmpty()) {
            Pair cur = pq.remove();
            if (cur.pos == -1 || (pos - cur.pos >= k)) {
                cur.pos = pos;
                sb.append(cur.c);
                cur.count--;
                if (cur.count > 0) {
                    if (k > 0)
                        posMap.put(pos, cur);
                    else
                        pq.add(cur);
                }
                pos++;
            }

            if (posMap.containsKey(pos-k)) {
                /**
                 * now they have satisfied the k-distance.
                 * Remove from posMap, and put back in priority-queue.
                 */
                Pair p = posMap.remove(pos-k);
                pq.add(p);
            }
        }

        if (pos == s.length())
            return sb.toString();
        return "";
    }

    public static void main(String[] args) {
        {
            String s = "aabbcc";
            System.out.println(new RearrangeStringKDistanceApart().rearrangeString(s, 3));
        }

        {
            String s = "aaabc";
            System.out.println(new RearrangeStringKDistanceApart().rearrangeString(s, 3));
        }

        {
            String s = "aa";
            System.out.println(new RearrangeStringKDistanceApart().rearrangeString(s, 0));
        }
    }
}
