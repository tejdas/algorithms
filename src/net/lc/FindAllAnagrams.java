package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 438
 * Two-pointer
 * Sliding window
 */
public class FindAllAnagrams {
    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() > s.length()) return new ArrayList<>();

        List<Integer> result = new ArrayList<>();

        int[] array = new int[26];
        int[] carray = new int[26];

        char[] parray = p.toCharArray();
        for (char c : parray) {
            int val = c - 'a';
            array[val]++;
        }

        int count = parray.length;

        char[] sarray = s.toCharArray();
        int left = -1;

        for (int i = 0; i < sarray.length; i++) {
            int val = sarray[i] - 'a';
            if (array[val] == 0) {
                /**
                 * letter not present in p, so reset the counters
                 */
                carray = new int[26];
                count = parray.length;
                left = -1;
            } else  if (carray[val] == array[val]) {
                for (int k = left; k < i; k++) {
                    int v = sarray[k] - 'a';
                    carray[v]--;
                    count++;
                    if (v == val) {
                        left = k+1;
                        break;
                    }
                }

                carray[val]++;
                count--;
            } else {
                if (left == -1) left = i;

                carray[val]++;
                count--;
                if (count == 0) {
                    result.add(left);

                    int v = sarray[left] - 'a';
                    carray[v]--;
                    left++;
                    count++;
                }
            }
        }
        return result;
    }
}
