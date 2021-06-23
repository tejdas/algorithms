package net.lc.design;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 1429
 */
public class FirstUnique {
    Queue<Integer> queue = new LinkedList<>();

    Map<Integer, Integer> countMap = new HashMap<>();
    public FirstUnique(int[] nums) {
        for (int val : nums) {
            add(val);
        }
    }

    public int showFirstUnique() {
        while (!queue.isEmpty()) {
            int val = queue.peek().intValue();
            int count = countMap.get(val);
            if (count == 1) return val;

            queue.poll();
        }
        return -1;
    }

    public void add(int val) {
        if (countMap.containsKey(val)) {
            countMap.put(val, 1 + countMap.get(val));
        } else {
            countMap.put(val, 1);
            queue.add(val);
        }
    }
}
