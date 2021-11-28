package net.lc.greedy;

import java.util.*;

/**
 * 945
 * Greedy
 */
public class MinIncrementToMakeArrayUnique {
    public int minIncrementForUnique(int[] A) {

        if (A == null || A.length == 0) return 0;

        if (A.length == 1) return 0;
        SortedMap<Integer, Integer> map = new TreeMap<>();
        int result = 0;

        for (int key : A) {
            if (map.containsKey(key)) {
                map.put(key, 1 + map.get(key));
            } else {
                map.put(key, 0);
            }
        }

        Set<Integer> keysToRemove = new HashSet<>();
        Set<Integer> numbers = new HashSet<>(map.keySet());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Integer s : keysToRemove) map.remove(s);

        if (map.isEmpty()) return 0;

        int next = map.firstKey().intValue();
        while (numbers.contains(next))
            next++;

        while (!map.isEmpty()) {
            if (!numbers.contains(next)) {
                int firstKey = map.firstKey();
                map.put(firstKey, map.get(firstKey)-1);

                if (next < firstKey) {
                    while (next < firstKey || numbers.contains(next)) {
                        next++;
                    }
                }

                result += (next - firstKey);
                numbers.add(next);
                if (map.get(firstKey) == 0) {
                    map.remove(firstKey);
                }
            }
            next++;
        }

        return result;
    }
}
