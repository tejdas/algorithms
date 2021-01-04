package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestCommonSubstring {
    static String[] generatePrefixStrings(final String[] inputs) {
        final List<String> strs = new ArrayList<>();
        for (final String s : inputs) {
            for (int i = 0; i < s.length(); i++) {
                strs.add(s.substring(i));
            }
        }
        final String[] array = strs.toArray(new String[strs.size()]);
        Arrays.sort(array);
        return array;
    }

    public static void findLargestSubstring(final String[] suffixStrs) {
        int largestIndex = 0;
        int largestSize = 0;
        for (int i = 0; i < suffixStrs.length-1; i++) {
            final String s1 = suffixStrs[i];
            final String s2 = suffixStrs[i+1];

            final char[] array1 = s1.toCharArray();
            final char[] array2 = s2.toCharArray();
            int m = 0;
            while (m < array1.length && m < array2.length) {
                if (array1[m] != array2[m])
                    break;
                m++;
            }
            if (m > largestSize) {
                largestSize = m;
                largestIndex = i;
            }
        }

        System.out.println("Largest substring: " + suffixStrs[largestIndex].substring(0, largestSize));
    }

    public static void main(final String[] args) {
        final String[] input = new String[] {"bcddasxyztaryamanpqr", "mndasxyyrindaryamancba"};
        final String[] suffixes = generatePrefixStrings(input);
        for (final String s : suffixes) {
            System.out.println(s);
        }
        findLargestSubstring(suffixes);
    }
}
