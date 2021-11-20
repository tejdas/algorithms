package net.lc;

/**
 * 152
 */
public class MaximumProductSubarray {
    public int maxProduct(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        if (array.length == 1) return array[0];
        /**
         * Store the abs of minProductSubarray seen so far.
         */
        int absMinProductSubarray = Integer.MAX_VALUE;

        int curProd = 1;

        /**
         * maxResult will never be negative with array-len > 1
         */
        int maxResult = 0;

        for (int val : array) {
            curProd = curProd * val;

            if (curProd == 0) {
                // reset
                curProd = 1;
                absMinProductSubarray = Integer.MAX_VALUE;
            } else if (curProd > 0) {
                maxResult =  Math.max(curProd, maxResult);
            } else { // curProd < 0
                if (Math.abs(curProd) < absMinProductSubarray) {
                    // update minProdSubArray
                    absMinProductSubarray = Math.abs(curProd);
                } else {
                    // trim from the left and update result
                    maxResult = Math.max(maxResult, curProd /-absMinProductSubarray);
                }
            }
        }
        return maxResult;
    }
}
