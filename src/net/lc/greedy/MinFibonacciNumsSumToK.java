package net.lc.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1414
 * https://leetcode.com/problems/find-the-minimum-number-of-fibonacci-numbers-whose-sum-is-k/submissions/
 * Binary-search
 * Greedy
 */
public class MinFibonacciNumsSumToK {
    public int findMinFibonacciNumbers(int k) {

        List<Integer> fibSeries = new ArrayList<>();
        fibSeries.add(1);

        int curfib = 2;
        while (curfib <= k) {
            int lastFib = fibSeries.get(fibSeries.size()-1);
            fibSeries.add(curfib);
            curfib = curfib + lastFib;
        }

        Integer[] fibs = fibSeries.toArray(new Integer[fibSeries.size()]);
        System.out.println(Arrays.toString(fibs));

        int count = 0;

        int remainder = k;

        while (remainder > 0) {
            int fib = findFibLessThanOrEq(fibs, remainder);
            System.out.println(fib);
            remainder -= fib;
            count++;
        }
        return count;
    }

    /**
     * Find a number in the sorted array that is less than or equal to num
     * BInarySearch
     * @param fibs
     * @param num
     * @return
     */
    private int findFibLessThanOrEq(Integer[] fibs, int num) {
        int left = 0;
        int right = fibs.length-1;

        if (num >= fibs[right]) return fibs[right];
        if (num == fibs[left]) return num;


        while (left <= right) {

            int mid = (left+right)/2;
            if (num == fibs[mid]) return num;

            if (num > fibs[mid]) {
                if (mid+1 < fibs.length && num <= fibs[mid+1]) {
                    if (num < fibs[mid+1]) return fibs[mid]; else return fibs[mid+1];
                } else {
                    left = mid+1;
                }
            } else {
                if (mid-1 >= 0 && num >= fibs[mid-1]) {
                    return fibs[mid-1];
                } else {
                    right = mid-1;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        new MinFibonacciNumsSumToK().findMinFibonacciNumbers(19);
    }
}
