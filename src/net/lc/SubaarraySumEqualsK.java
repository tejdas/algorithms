package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 560
 * Range
 * HashMap
 * Key concept:
 * sum[i,j] = sum[0,j] - sum[0,i]
 * If sum[i,j] = k, it means
 * k = sum[0,j] - sum[0,i]
 * For any sum [0,j], check to see the count of how many times we have seen: sum-k
 */
public class SubaarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        Map< Integer, Integer > map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
