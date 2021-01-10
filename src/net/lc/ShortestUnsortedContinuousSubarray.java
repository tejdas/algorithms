package net.lc;

/**
 * 581
 */
public class ShortestUnsortedContinuousSubarray {
    public int findUnsortedSubarray(int[] a) {
        if (a == null || a.length == 0) return 0;

        int i = 0;
        int j = a.length-1;

        while (i < a.length-1 && a[i] <= a[i+1]) i++;
        if (i == a.length-1) return 0;

        while (j > 0 && a[j] >= a[j-1]) j--;
        if (j == 0) return 0;

        int minval = Integer.MAX_VALUE;
        int maxval = Integer.MIN_VALUE;

        for (int index = i; index <=j; index++) {
            minval = Math.min(minval, a[index]);
            maxval = Math.max(maxval, a[index]);
        }

        while (i > 0 && a[i-1] > minval) i--;
        while (j < a.length-1 && a[j+1] < maxval) j++;

        return (j-i+1);
    }
}
