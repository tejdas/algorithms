package net.lc;

/**
 * 121
 */
public class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] stock) {
        if (stock == null) {
            return 0;
        }
        int maxProfit = 0;
        int lastMin = Integer.MAX_VALUE;

        for (final int val : stock) {
            if (val < lastMin) {
                lastMin = val;
            } else {
                final int profit = val - lastMin;
                maxProfit = Math.max(maxProfit, profit);
            }
        }

        return maxProfit;
    }
}