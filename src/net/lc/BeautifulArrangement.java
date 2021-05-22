package net.lc;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/beautiful-arrangement/submissions/
 * DFS Recursion
 * 526
 */
public class BeautifulArrangement {
    private int count = 0;
    private int n;
    private int[] perm;

    public int countArrangement(int n) {
        this.n = n;
        perm = new int[n+1];
        perm[0] = 0;
        for (int i = 1; i <= n; i++) {
            perm[i] = i;
        }

        countArrangementRecurse(1);
        return count;

    }

    private void countArrangementRecurse(int startIndex) {
        if (startIndex == perm.length) {
            System.out.println(Arrays.toString(perm));
            count += 1;
            return;
        }

        for (int index = startIndex; index < perm.length; index++) {
            swap(startIndex, index);
            boolean flag = ((perm[startIndex] % startIndex == 0) || (startIndex % perm[startIndex] == 0));

            if (flag) {
                countArrangementRecurse(startIndex+1);
            }

            swap(startIndex, index);
        }
    }
    private void swap(int i, int j) {
        int temp = perm[i];
        perm[i] = perm[j];
        perm[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println(new BeautifulArrangement().countArrangement(6));
    }
}
