package net.lc;

import java.util.Arrays;

/**
 * 828
 * Dynamic Programming
 * Linear O(n)
 *
 * Store last occurrence index of every character A .. Z
 *
 * dpRes[i] -> count of unique chars for all substrings ending at index i
 *
 * For index i, there will be (i+1) substrings ending at index i
 *
 * Let's say the character at index i is c.
 * Three cases
 *
 * Case 1:
 * c is occurring for the first time (last occurrence -1)
 * In this case, c is the newly added unique char in every substring ending at i.
 * Which means:
 * dpRes[i] = dpRes[i-1] + (i+1) (add 1 for every substring ending at i)
 *
 * Case 2:
 * c has occurred ONLY once at an earlier index j (j < i)
 * In this case,
 * (a) c is unique in each substring that begin AFTER j and ending at i.
 * (b) c is NOT unique in each substring that begin at 0 thru j and ending at i
 *
 * Which means:
 * dpRes[i] = dpRes[i-1] + (i-lastPos) - (lastPos+1)
 *
 * Case 3:
 * c has occurred more than once at an earlier indices: j1, j2 < i, and j1 < j2
 * In this case, for dpRes[i-1],
 * c was NOT unique for all substrings beginning at (0...j1)
 * c was unique for all substrings beginning at (j1+1, j2)
 *
 * So, for dpRes[i], c is no longer unique for all substrings beginning at (j1+1, j2)
 * We must exclude it.
 * Which means:
 * dpRes[i] = dpRes[i-1] + (i-lastPos) - (j2-j1+1)
 *
 * Answer is sum of dpRes[i] (for i in 0 ... len-1)
 */
public class CountUniqueCharsOfAllSubstrings {
    int[] pos = new int[26];
    int[] ppos = new int[26];
    int[] count = new int[26];
    public int uniqueLetterString(String s) {
        if (s.isEmpty()) return 0;
        Arrays.fill(pos, -1);
        Arrays.fill(ppos, -1);

        int[] dpRes = new int[s.length()];

        dpRes[0] = 1;
        char[] array = s.toCharArray();
        pos[array[0] - 'A'] = 0;
        count[array[0] - 'A']++;
        int result = dpRes[0];

        for (int i = 1; i < array.length; i++) {
            char c = array[i];
            int lastPos = pos[c - 'A'];
            if (lastPos == -1) {
                // case (1)
                dpRes[i] = dpRes[i-1] + (i+1);
            } else {
                int charCount = count[c - 'A'];

                if (charCount == 1) {
                    // exclude for c because it is no longer unique
                    // case(2)
                    dpRes[i] = dpRes[i-1] - (lastPos+1) + (i-lastPos);
                } else {
                    // case (2)
                    int diff = pos[c - 'A'] - ppos[c - 'A'];
                    dpRes[i] = dpRes[i-1] - diff + (i - lastPos);
                }
            }
            ppos[c - 'A'] = pos[c - 'A'];
            pos[c - 'A'] = i;
            count[c - 'A']++;
            result += dpRes[i];
        }

        return result % 1000000007;
    }

    public static void main(String[] args) {

        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("A"));
        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("AB"));
        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("ABC"));
        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("ABA"));

        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("LEETCODE"));


        //System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("ABAB"));
        System.out.println(new CountUniqueCharsOfAllSubstrings().uniqueLetterString("ABABAB"));
    }
}
