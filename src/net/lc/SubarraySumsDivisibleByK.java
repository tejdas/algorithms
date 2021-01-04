package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 974
 */
public class SubarraySumsDivisibleByK {
    private final Map<Integer, Integer> map = new HashMap<>();
    private int[] sumArray;
    int result;

    public int subarraysDivByK(int[] array, int K) {
        sumArray = new int[array.length+1];

        int sum = 0;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            sumArray[i + 1] = sum;
        }

        /**
         * If sum(0,i) % K == n
         * and
         * sum(0,j) % K == n
         * Then sum(i,j) is divisible by K.
         *
         * So, start finding all 0-indexed sums ranges from (0...i)
         *
         * Count all subranges with same remainder in a map.
         * If there are n subranges with same remainder, it means there are
         * n * (n-1) / 2 ranges.
         */
        for (int val : sumArray) {

            int rem = (val % K + K) % K;

            if (!map.containsKey(rem)) {
                map.put(rem, 1);
            } else {
                map.put(rem, 1 + map.get(rem));
            }
        }

        for (int val : map.values()) {
            if (val > 0) {
                int v = (val * (val-1)) / 2;
                result += v;
            }
        }
        return result;
    }
}
