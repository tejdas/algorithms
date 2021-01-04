package net.lc;

import java.util.Arrays;

/**
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
