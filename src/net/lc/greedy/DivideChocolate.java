package net.lc.greedy;

/**
 * 1231
 * Greedy
 * Binary-search on possible answers
 */
public class DivideChocolate {
    private int[] array;
    private int k;

    public int maximizeSweetness(int[] sweetness, int K) {
        if (sweetness == null || sweetness.length == 0) return 0;
        this.array = sweetness;
        this.k = K+1;

        int totalSum = 0;
        int smallestVal = Integer.MAX_VALUE;

        for (int val : array) {
            totalSum += val;
            smallestVal = Math.min(smallestVal, val);
        }

        /**
         * Binary-search on all possible values between smallest single number, and total sum
         */
        return search(smallestVal, totalSum, k);
    }

    /**
     * search for the max possible value between min and max.
     * min is possible.
     * @param min
     * @param max
     * @param m
     * @return
     */
    private int search(int min, int max, int m) {
        int left = min;
        int right = max;

        int maxIsPossible = left;
        while (left <= right) {
            int mid = (left + right)/2;

            boolean flag = isPossible(array, mid, m);
            if (flag) {
                // mid is possible. So, update maxIsPossible
                maxIsPossible = Math.max(mid, maxIsPossible);
                left = mid+1;
            } else {
                right = mid-1;
            }
        }

        return maxIsPossible;
    }

    /**
     * Return true if it's possible to segment the array into k parts, with each part >= minLimit
     * @param array
     * @param minLimit
     * @param k
     * @return
     */
    private boolean isPossible(int[] array, int minLimit, int k) {
        int subArrayCount = 0;
        int runningSum = 0;

        int index;
        for (index = 0; index < array.length; index++) {
            runningSum += array[index];
            if (runningSum >= minLimit) {
                subArrayCount++;
                runningSum = 0;
            }
        }
        return (subArrayCount >= k);
    }

    public static void main(String[] args) {
        {
            int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
            System.out.println(new DivideChocolate().maximizeSweetness(array, 5));
        }

        {
            int[] array = {5,6,7,8,9,1,2,3,4 };
            System.out.println(new DivideChocolate().maximizeSweetness(array, 8));
        }
        {
            int[] array = { 1,2,2,1,2,2,1,2,2 };
            System.out.println(new DivideChocolate().maximizeSweetness(array, 2));
        }
    }
}
