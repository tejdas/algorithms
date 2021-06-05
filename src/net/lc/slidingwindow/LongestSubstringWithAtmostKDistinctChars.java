package net.lc.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 340
 * Sliding Window
 */
public class LongestSubstringWithAtmostKDistinctChars {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        if (k >= s.length()) return s.length();
        char[] array = s.toCharArray();

        int leftPos = 0;
        int rightPos = -1;
        int maxLength = 0;
        Map<Character, Integer> lastOccurance = new HashMap<>();
        boolean found = false;

        for (int i = 0; i < array.length; i++) {
            char c = array[i];

            if (lastOccurance.containsKey(c)) {
                rightPos++;
            } else {
                rightPos++;
                if (lastOccurance.size() == k) {
                    /**
                     * we have already found k distinct chars. Before taking the new char, try to see
                     * if we can move the left edge of the window, and skip past another distinct char,
                     * so as to be able to accommodate current chafr.
                     */
                    while (leftPos < rightPos) {
                        if (lastOccurance.get(array[leftPos]) == leftPos) {
                            lastOccurance.remove(array[leftPos]);
                            leftPos++;
                            break;
                        }
                        leftPos++;
                    }
                }
            }

            lastOccurance.put(c, i);
            if (lastOccurance.size() == k) {
                found = true;
                maxLength = Math.max(maxLength, rightPos-leftPos+1);
            }
        }
        if (!found) return s.length();
        return maxLength;
    }
}
