package net.lc.unionfind;

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
    static class Range {
        int low;
        int high;

        public Range(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public int getLength() {
            return high-low+1;
        }
    }

    /**
     * We are trying to maintain range-map here.
     * If there is a range(m,n) -> [m,.....,n]
     * we will maintain a map keyed with the boundary elements
     * m -> range(m,n)
     * n -> range(m,n)
     */
    private final Map<Integer, Range> map = new HashMap<>();

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
                /**
                 * Element is sandwiched between two ranges
                 */
                Range lower = map.remove(val-1);
                Range higher = map.remove(val+1);
                Range range = new Range(lower.low, higher.high);
                map.put(lower.low, range);
                map.put(higher.high, range);
                longestSequence = Math.max(range.getLength(), longestSequence);
            } else if (map.containsKey(val-1)) {
                /**
                 * Element touches one range on left
                 */
                Range lower = map.remove(val-1);
                Range range = new Range(lower.low, val);
                map.put(lower.low, range);
                map.put(val, range);
                longestSequence = Math.max(range.getLength(), longestSequence);
            } else if (map.containsKey(val+1)) {
                /**
                 * Element touches one range on right
                 */
                Range higher = map.remove(val+1);
                Range range = new Range(val, higher.high);
                map.put(val, range);
                map.put(higher.high, range);
                longestSequence = Math.max(range.getLength(), longestSequence);
            } else {
                /**
                 * Element is not adjacent to any ranges; create a range with the current element only
                 */
                Range range = new Range(val, val);
                map.put(val, range);
                longestSequence = Math.max(range.getLength(), longestSequence);
            }
        }
        return longestSequence;
    }

    public static void main(String[] args) {
        int[] input = {-3,2,8,5,1,7,-8,2,-8,-4,-1,6,-6,9,6,0,-7,4,5,-4,8,2,0,-2,-6,9,-4,-1};

        System.out.println(new LongestConsecutiveSequence().longestConsecutive(input));
    }
}
