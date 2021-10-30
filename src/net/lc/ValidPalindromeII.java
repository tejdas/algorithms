package net.lc;

/**
 * Incomplete
 */
public class ValidPalindromeII {
        public boolean validPalindrome2(String s) {
            char[] array = s.toCharArray();
            boolean[] barray = new boolean[26];

            for (char c : array) {
                int index = c - 'a';
                barray[index] = !barray[index];
            }

            int left = 0;
            int right = array.length-1;

            int countRemoved = 0;

            while (left < right) {
                if (array[left] == array[right]) {
                    left++;
                    right--;
                } else {
                    int lindex = array[left] - 'a';
                    int rindex = array[right] - 'a';

                    if (barray[lindex]) {
                        left++;
                        countRemoved++;
                        barray[lindex] = false;
                    }
                    if (barray[rindex]) {
                        right--;
                        countRemoved++;
                        barray[rindex] = false;
                    }
                }
            }
            if (left == right) return countRemoved <=1;
            return countRemoved<=2;
        }

    public boolean validPalindrome(String s) {
        char[] array = s.toCharArray();
        int[] carray = new int[26];
        for (char c : array) {
            int index = c - 'a';
            carray[index]++;
        }

        int left = 0;
        int right = array.length-1;

        int countRemoved = 0;

        while (left < right) {
            if (array[left] == array[right]) {
                left++;
                right--;
            } else {
                int lindex = array[left] - 'a';
                int rindex = array[right] - 'a';
                if (carray[lindex] %2 == 0 && carray[rindex] %2 == 0) {
                    left++;
                    countRemoved++;
                    continue;
                }

                if (carray[lindex] %2 == 1) {
                    left++;
                    countRemoved++;
                }
                if (carray[rindex] %2 == 1) {
                    right--;
                    countRemoved++;
                }
            }
        }
        if (left == right) return countRemoved <=2;
        return countRemoved<=1;
    }

    public static void main(String[] args) {
        System.out.println(new ValidPalindromeII().validPalindrome("abca"));
        System.out.println(new ValidPalindromeII().validPalindrome("abc"));
        System.out.println(new ValidPalindromeII().validPalindrome("aba"));
        System.out.println(new ValidPalindromeII().validPalindrome("eedede"));
    }
}
