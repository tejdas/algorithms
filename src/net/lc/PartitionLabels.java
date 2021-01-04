package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 763
 * Greedy
 * Two-pointer
 * Sliding window
 */
public class PartitionLabels {
    public List<Integer> partitionLabels(String s) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0)
            return result;

        int[] posarray = new int[26];
        Arrays.fill(posarray, -1);
        char[] array = s.toCharArray();

        /**
         * posarray stores the LAST occurrence of each character.
         */
        for (int i = 0; i < array.length; i++) {
            int val = array[i] - 'a';
            posarray[val] = i;
        }

        int beginIndex = 0;
        int endIndex = -1;
        for (int i = 0; i < array.length; i++) {
            int val = array[i] - 'a';
            if (posarray[val] > endIndex) {
                /**
                 * keep extending the right-edge of window to include
                 * the last occurrence of current character seen.
                 */
                endIndex = posarray[val];
            }

            /**
             * Once the current-index reaches the right-edge,
             * compute interval size, and reset window pointers.
             */
            if (i == endIndex) {
                int size = endIndex - beginIndex + 1;
                result.add(size);
                beginIndex = i + 1;
                endIndex = -1;
            }
        }

        return result;
    }
}
