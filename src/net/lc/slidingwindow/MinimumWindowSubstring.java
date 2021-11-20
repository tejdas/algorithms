package net.lc.slidingwindow;

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
        int[] tmap = new int[64];
        for (char c : array) {
            tmap[c - 'A']++;
        }

        int minPos = -1;
        int maxPos = -1;

        int curMin = -1;
        int curMax = -1;

        /**
         * Store the chars seen so far in s, that belong to t.
         */
        int[] map = new int[64];
        int missingCount = t.length();

        for (int i = 0; i < sarray.length; i++) {
            char c = sarray[i];

            int idx = c - 'A';

            if (missingCount > 0) {
                /**
                 * Haven't found the right-edge of the window yet
                 */
                if (tmap[c - 'A'] == 0) {
                    continue;
                }


                if (map[idx] > 0) {
                    if (map[idx] < tmap[idx]) {
                        missingCount--;
                    }
                    map[idx]++;
                } else {
                    map[idx]++;
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
                if (tmap[idx] == 0) {
                    continue;
                }
                map[idx]++;
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
                int pidx = pc - 'A';
                if (map[pidx] > 0) {
                    /**
                     * We are at the exact number of letters of pc.
                     * Can't shrink on the left anymore.
                     */
                    if (map[pidx] <= tmap[pidx]) {
                        break;
                    } else {
                        map[pidx]--;
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
