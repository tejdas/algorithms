package net.lc.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 76
 * Two-pointer
 * Sliding-Window
 */
public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        char[] array = t.toCharArray();
        char[] sarray = s.toCharArray();
        /**
         * Store the frequency of each char in t
         * char -> frequency
         */
        Map<Character, Integer> tmap = new HashMap<>();
        for (char c : array) {
            if (tmap.containsKey(c)) {
                tmap.put(c, 1 + tmap.get(c));
            } else {
                tmap.put(c, 1);
            }
        }

        int minPos = -1;
        int maxPos = -1;

        int curMin = -1;
        int curMax = -1;

        /**
         * Store the chars seen so far in s, that belong to t.
         */
        Map<Character, Integer> map = new HashMap<>();
        int missingCount = t.length();

        for (int i = 0; i < sarray.length; i++) {
            char c = sarray[i];

            if (missingCount > 0) {
                /**
                 * Haven't found the right-edge of the window yet
                 */
                if (!tmap.containsKey(c)) {
                    continue;
                }

                if (map.containsKey(c)) {
                    if (map.get(c) < tmap.get(c)) {
                        missingCount--;
                    }
                    map.put(c, 1 + map.get(c));
                } else {
                    map.put(c, 1);
                    missingCount--;
                }

                if (missingCount == 0) {
                    /**
                     * We found our first window, containing all chars from t.
                     */
                    curMin = 0;
                    curMax = i;
                    minPos = 0;
                    maxPos = i;
                }
            } else {
                /**
                 * Already found the right-edge of the window. Now, just expand right-edge to the right.
                 */
                curMax++;
                if (!tmap.containsKey(c)) {
                    continue;
                }
                map.put(c, 1 + map.get(c));
            }

            if (missingCount >0) continue;

            /**
             * Now, try to shrink the left-window, by going past extra letters in map.
             * Essentially, for each letter, check to see if the current window (map)
             * contains more letters than is there in t.
             */
            int j;
            for (j = curMin; j < curMax; j++) {
                char pc = sarray[j];
                if (map.containsKey(pc)) {
                    /**
                     * We are at the exact number of letters of pc.
                     * Can't shrink on the left anymore.
                     */
                    if (map.get(pc) <= tmap.get(pc)) {
                        break;
                    } else {
                        map.put(pc, map.get(pc)-1);
                    }
                }
            }

            if (j > curMin) {
                /**
                 * we were able to shrink from left. So, recompute min window.
                 */
                curMin = j;

                if (curMax-curMin < maxPos-minPos) {
                    minPos = curMin;
                    maxPos = curMax;
                }
            }
        }

        if (missingCount == 0) {
            return s.substring(minPos, maxPos + 1);
        } else {
            return "";
        }
    }
}
