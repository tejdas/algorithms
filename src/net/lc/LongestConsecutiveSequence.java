package net.lc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 128
 * Union-Find
 * https://leetcode.com/problems/longest-consecutive-sequence/
 */
public class LongestConsecutiveSequence {
    static class Info {
        int low;
        int high;

        public Info(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public int getLength() {
            return high-low+1;
        }
    }

    private final Map<Integer, Info> map = new HashMap<>();

    public int longestConsecutive(int[] array) {
        if (array == null || array.length == 0) return 0;

        Set<Integer> set = new HashSet<>();
        int longestSequence = 0;
        for (int val : array) {
            if (set.contains(val)) continue; else set.add(val);
            if (map.containsKey(val)) {
                continue;
            }
            if (map.containsKey(val-1) && map.containsKey(val+1)) {
                Info lower = map.remove(val-1);
                Info higher = map.remove(val+1);
                Info info = new Info(lower.low, higher.high);
                map.put(lower.low, info);
                map.put(higher.high, info);
                longestSequence = Math.max(info.getLength(), longestSequence);
            } else if (map.containsKey(val-1)) {
                Info lower = map.remove(val-1);
                Info info = new Info(lower.low, val);
                map.put(lower.low, info);
                map.put(val, info);
                longestSequence = Math.max(info.getLength(), longestSequence);
            } else if (map.containsKey(val+1)) {
                Info higher = map.remove(val+1);
                Info info = new Info(val, higher.high);
                map.put(val, info);
                map.put(higher.high, info);
                longestSequence = Math.max(info.getLength(), longestSequence);
            } else {
                Info info = new Info(val, val);
                map.put(val, info);
                longestSequence = Math.max(info.getLength(), longestSequence);
            }
        }
        return longestSequence;
    }

    public static void main(String[] args) {
        int[] input = {-3,2,8,5,1,7,-8,2,-8,-4,-1,6,-6,9,6,0,-7,4,5,-4,8,2,0,-2,-6,9,-4,-1};

        System.out.println(new LongestConsecutiveSequence().longestConsecutive(input));
    }
}
