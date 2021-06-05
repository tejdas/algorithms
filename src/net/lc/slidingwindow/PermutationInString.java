package net.lc.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 567
 * Sliding Window
 */
public class PermutationInString {
    public boolean checkInclusion(String s2, String s1) {
        Map<Character, Integer> map = new HashMap<>();
        char[] s2array = s2.toCharArray();
        for (char c : s2array) {
            map.computeIfAbsent(c, k -> new Integer(0));
            map.put(c, 1 + map.get(c));
        }

        char[] s1array = s1.toCharArray();

        int missingCount = s2array.length;
        Map<Character, Integer> runningMap = new HashMap<>();

        Map<Character, Integer> posMap = new HashMap<>();

        for (int i = 0; i < s1array.length; i++) {
            char c = s1array[i];

            if (!map.containsKey(c)) {
                missingCount = s2array.length;
                runningMap.clear();
                posMap.clear();
                continue;
            }

            if (!runningMap.containsKey(c)) {
                if (!posMap.containsKey(c)) {
                    posMap.put(c, i);
                }
                runningMap.put(c, 1);
                missingCount--;
                if (missingCount == 0) return true;
            } else if (runningMap.get(c).intValue() < map.get(c).intValue()) {
                if (!posMap.containsKey(c)) {
                    posMap.put(c, i);
                }
                runningMap.put(c, 1 + runningMap.get(c));
                missingCount--;
                if (missingCount == 0) return true;
            } else {
                // rebuild
                int index = posMap.get(c);
                posMap.clear();
                runningMap.clear();
                missingCount = s2array.length;
                for (int j = index+1; j <= i; j++) {
                    char c1 = s1array[j];

                    if (!posMap.containsKey(c1)) {
                        posMap.put(c1, j);
                    }
                    if (runningMap.containsKey(c1)) {
                        runningMap.put(c1, 1 + runningMap.get(c1));
                    } else {
                        runningMap.put(c1, 1);
                    }
                    missingCount--;
                }
                if (missingCount == 0) return true;
            }
        }
        return false;
    }
}
