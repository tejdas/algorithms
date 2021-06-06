package net.lc.slidingwindow;

import java.util.Arrays;

/**
 * Sliding Window
 * 3
 */
public class LongestSubstringWithoutRepeatingChars {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int maxLength = 0;
        int curLength = 0;
        int startPos = -1;
        char[] array = s.toCharArray();
        int[] posArray = new int[128];
        Arrays.fill(posArray, -1);

        for (int pos = 0; pos < array.length; pos++) {
            char c = array[pos];
            if (posArray[c] != -1 && posArray[c] >= startPos) {
                /**
                 * we have seen the char before, and are seeing now again. So, reset startPos to
                 * last occurrence of the char + 1 (to skip the earlier occurrence).
                 * E.g. let's say we are considering t
                 * We saw t at pos 12 and again now (pos 18)
                 * Reset startPos to 13.
                 */
                maxLength = Math.max(maxLength, curLength);
                startPos = posArray[c] + 1;
                posArray[c] = pos;
                curLength = pos - startPos + 1;
            } else {
                posArray[c] = pos;
                curLength++;
            }
        }

        maxLength = Math.max(maxLength, curLength);
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubstringWithoutRepeatingChars().lengthOfLongestSubstring("abcabcbb"));
        System.out.println(new LongestSubstringWithoutRepeatingChars().lengthOfLongestSubstring("pwwkew"));
        System.out.println(new LongestSubstringWithoutRepeatingChars().lengthOfLongestSubstring("bbbbb"));
        System.out.println(new LongestSubstringWithoutRepeatingChars().lengthOfLongestSubstring("cdd"));
    }
}
