package net.lc;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/submissions/
 * Dynamic Programming
 * O(1)
 * State transition
 */
public class BestTimeToBuyAndSellStockWithFee {
    public int maxProfit(int[] prices, int fee) {
        /**
         * Four states:
         * BUY
         * HOLD (after BUY)
         * SELL
         * IDLE (after SELL)
         *
         * BUY -> SELL
         * BUY -> HOLD
         * HOLD -> HOLD
         * HOLD -> SELL
         * SELL -> BUY
         * SELL -> IDLE
         * IDLE -> IDLE
         * IDLE -> BUY
         */
        int[] bestPriceWithBuy = new int[prices.length];
        int[] bestPriceWithSell = new int[prices.length];
        int[] bestPriceWithHold = new int[prices.length];
        int[] bestPriceWithIdle = new int[prices.length];

        bestPriceWithHold[0] = Integer.MIN_VALUE; // can't buy prior and held at index-0
        bestPriceWithIdle[0] = 0;
        bestPriceWithBuy[0] = -prices[0];
        bestPriceWithSell[0] = Integer.MIN_VALUE; // can't buy prior and sell at index-0

        for (int i = 1; i < prices.length; i++) {
            bestPriceWithHold[i] = Math.max(bestPriceWithHold[i-1], bestPriceWithBuy[i-1]);
            bestPriceWithIdle[i] = Math.max(bestPriceWithIdle[i-1], bestPriceWithSell[i-1]);

            int buyAfterSell = bestPriceWithSell[i-1] == Integer.MIN_VALUE? -prices[i] : bestPriceWithSell[i-1] - prices[i];
            int buyAfterIdle = bestPriceWithIdle[i-1] - prices[i];

            bestPriceWithBuy[i] = Math.max(buyAfterIdle, buyAfterSell);

            int sellAfterBuy = bestPriceWithBuy[i-1] +prices[i] - fee;
            int sellAfterHold = bestPriceWithHold[i-1] == Integer.MIN_VALUE? Integer.MIN_VALUE : bestPriceWithHold[i-1] +prices[i] - fee;

            bestPriceWithSell[i] = Math.max(sellAfterBuy, sellAfterHold);
        }
        return Math.max(bestPriceWithIdle[prices.length-1], bestPriceWithSell[prices.length-1]);
    }

    public static void main(String[] args) {
        {
            int[] prices = { 1, 3, 2, 8, 4, 9 };
            System.out.println(new BestTimeToBuyAndSellStockWithFee().maxProfit(prices, 2));
        }

        {
            int[] prices = {1,1,1,3,5,2,5,4,3,2};
            System.out.println(new BestTimeToBuyAndSellStockWithFee().maxProfit(prices, 4));
        }
    }
}
