package net.lc;

public class MaximumProductSubarray {
    public int maxProduct(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        if (array.length == 1) return array[0];
        int absMinNeg = Integer.MAX_VALUE;

        int curProd = 1;

        int result = 0;

        for (int val : array) {
            curProd = curProd * val;

            if (curProd > 0) {
                result =  Math.max(curProd, result);
            } else if (curProd < 0) {
                if (Math.abs(curProd) < absMinNeg) {
                    absMinNeg = Math.abs(curProd);
                } else {
                    result = Math.max(result, curProd /-absMinNeg);
                }
            } else {
                curProd = 1;
                absMinNeg = Integer.MAX_VALUE;
            }
        }
        return result;
    }
}
