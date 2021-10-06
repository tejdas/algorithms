package net.lc;

public class MaxPalindromeLengthFromSSubsequences {

    int[][] memo =  null;

    int maxI = -1;
    int maxJ = -1;
    int longestLength = 0;

    int boundary;

    int maxPaliLength = 1;

    public int longestPalindrome(String text1, String text2) {

        memo = new int[text1.length() + 1][text2.length() + 1];

        char[] a1 = text1.toCharArray();
        char[] a2 = text2.toCharArray();

        char[] array = new char[a1.length + a2.length];

        System.arraycopy(a1, 0, array, 0, a1.length);
        System.arraycopy(a2, 0, array, a1.length, a2.length);
        boundary = a1.length-1;

        //System.out.println("boundary: " + boundary);

        longestPalindromeSubseq(array);
        if (maxPaliLength > 1) return maxPaliLength;
        return 0;
    }
    public int longestPalindrome2(String text1, String text2) {

        memo = new int[text1.length()+1][text2.length()+1];

        char[] a1 = text1.toCharArray();
        char[] a2 = text2.toCharArray();
        reverse(a2);

        for (int i = 1; i <= a1.length; i++)  {
            for (int j = 1; j <= a2.length; j++)  {

                int v1 = memo[i-1][j];
                v1 = Math.max(memo[i][j-1], v1);

                if (a1[i-1] == a2[j-1]) {
                    v1 = Math.max(v1,  1 + memo[i-1][j-1]);
                }

                memo[i][j] = v1;
                if (memo[i][j]  > longestLength) {
                    longestLength  =  memo[i][j];
                    maxI = i-1;
                    maxJ = j-1;
                }

            }
        }

        //System.out.println(longestLength);
        if (longestLength == 0) return 0;

        if (maxI < a1.length-1) {
            char[] parray = new char[a1.length-1-maxI];
            System.arraycopy(a1, maxI+1, parray, 0, parray.length);
            int plen = longestPalindromeSubseq(parray);

            return longestLength*2 + plen;
        } else if (maxJ < a2.length-1) {
            char[] parray = new char[a2.length-1-maxJ];
            System.arraycopy(a2, maxJ+1, parray, 0, parray.length);
            int plen = longestPalindromeSubseq(parray);

            return longestLength*2 + plen;
        }
        else {
            return longestLength*2;
        }

    }

    private void reverse(char[] arrayToReverse) {
        int left = 0;
        int right = arrayToReverse.length-1;
        while (left < right) {
            char temp = arrayToReverse[left];
            arrayToReverse[left] = arrayToReverse[right];
            arrayToReverse[right] = temp;
            left++;
            right--;
        }
    }

    private int[][] matrix;

    static boolean isPalindrome(char[] array) {
        int i = 0;
        int j = array.length - 1;

        while (i < j) {
            if (array[i] != array[j]) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public int longestPalindromeSubseq(char[] array) {
        if (isPalindrome(array)) {
            return array.length;
        }

        matrix = new int[array.length][array.length];

        for (int size = 1; size <= array.length; size++) {
            for (int startPos = 0; startPos < array.length - size + 1; startPos++) {
                int endPos = startPos + size - 1;

                if (endPos == startPos) {
                    matrix[startPos][endPos] = 1;
                    continue;
                } else if (endPos == startPos+1) {
                    if (array[startPos] == array[endPos]) {
                        matrix[startPos][endPos] = 2;
                        if (startPos <= boundary && endPos > boundary) {
                            maxPaliLength = Math.max(maxPaliLength, matrix[startPos][endPos]);
                        }
                    } else {
                        matrix[startPos][endPos] = 1;
                    }
                    continue;
                }

                int l1 = 0;
                if (array[startPos] == array[endPos]) {
                    l1 = 2 + matrix[startPos + 1][endPos - 1];

                    if (startPos <= boundary && endPos > boundary) {
                        maxPaliLength = Math.max(maxPaliLength, l1);
                    }
                }
                int l2 = matrix[startPos + 1][endPos];

                if (l2 > l1) {
                    if (startPos+1 < boundary && endPos > boundary) {
                       // maxPaliLength = Math.max(maxPaliLength, l2);
                    }
                }
                int l3 = matrix[startPos][endPos - 1];
                if (l3 > Math.max(l1, l2)) {
                    if (startPos < boundary && endPos-1 > boundary) {
                        //maxPaliLength = Math.max(maxPaliLength, l3);
                    }
                }

                matrix[startPos][endPos] = Integer.max(l1, Integer.max(l2, l3));
            }
        }
        return matrix[0][array.length - 1];
    }

    public static void main(String[] args) {

        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("cacb", "cbba"));
        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("ab", "ab"));
        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("aa", "bb"));
        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("afaaadacb", "ca"));

        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("aba", "cfc"));
        System.out.println(new MaxPalindromeLengthFromSSubsequences().longestPalindrome("c", "fffaeacefcefeecedeedefecdcbedebebcfadffeacddcffa"));

   /*
   "c"
""
    */
    }
}
