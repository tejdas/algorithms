package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/submissions/
 * Map
 */
public class PairsOfSongs {
    Map<Integer, Integer> map = new HashMap<>();
    public int numPairsDivisibleBy60(int[] time) {
        for (int t : time) {
            int rem = t % 60;
            map.put(rem, 1 + map.getOrDefault(rem, 0));
        }

        int sum = 0;
        for (int i = 1; i <= 29; i++) {
            int val1 = map.getOrDefault(i, 0);
            int val2 = map.getOrDefault(60-i, 0);

            sum += val1 * val2;
        }

        int val0 = map.getOrDefault(0, 0);
        if (val0 > 1) {
            sum += (val0 * (val0 - 1)) / 2;
        }

        int val30 = map.getOrDefault(30, 0);
        if (val30 > 1) {
            sum += (val30 * (val30 - 1)) / 2;
        }
        return sum;
    }

    public static void main(String[] args) {
        {
            int[] time = { 30, 20, 150, 100, 40 };
            System.out.println(new PairsOfSongs().numPairsDivisibleBy60(time));
        }

        {
            int[] time = { 60,60,60 };
            System.out.println(new PairsOfSongs().numPairsDivisibleBy60(time));
        }
    }
}
