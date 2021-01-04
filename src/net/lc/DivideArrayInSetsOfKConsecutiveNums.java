package net.lc;

import java.util.SortedMap;
import java.util.TreeMap;

public class DivideArrayInSetsOfKConsecutiveNums {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums == null || nums.length == 0) return true;

        if (k == 1) return true;

        if (k > nums.length) return false;

        if (nums.length % k != 0) return false;

        SortedMap<Integer, Integer> map = new TreeMap<>();
        for (int val : nums) {
            if (map.containsKey(val)) {
                map.put(val, 1 + map.get(val));
            } else {
                map.put(val, 1);
            }
        }

        while (!map.isEmpty()) {
            int key = map.firstKey();

            for (int i = 0; i < k; i++) {
                if (map.containsKey(key+i)) {
                    int existingVal = map.get(key+i);
                    existingVal--;
                    if (existingVal == 0) map.remove(key+i);
                    else {
                        map.put(key + i, existingVal);
                    }
                } else {
                    return false;
                }
            }
        }

        return map.isEmpty();
    }
}
