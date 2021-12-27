package net.lc;

/**
 * 121
 * Greedy
 */
public class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] stock) {
        if (stock == null) {
            return 0;
        }
        int maxProfit = 0;
        int lastMin = Integer.MAX_VALUE;

        /**
         * Scan the stocks in O(n)
         * If we find a stock value < than lastMin, this becomes our new lastMin (buyPrice)
         * If we find any stock price greater than current lastMin, this is a potential sell price.
         * We calculate the difference, and update maxProfit
         */
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