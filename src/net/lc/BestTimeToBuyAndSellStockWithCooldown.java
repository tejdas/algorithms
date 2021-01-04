package net.lc;

/**
 * 309
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/submissions/
 * Dynamic Programming
 * State-transition diagram
 * CD1 => Optional cool-down after buy
 * CD2 => Mandatory cool-down after sell
 *
 * B -> CD1
 * B -> S
 * CD1 -> CD1
 * CD1 -> S
 * S -> CD2
 * CD2 -> CD2
 * CD2 -> B
 */
public class BestTimeToBuyAndSellStockWithCooldown {
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;

        int[] bestPriceWithCD1 = new int[prices.length];
        int[] bestPriceWithCD2 = new int[prices.length];
        int[] bestPriceWithBuy = new int[prices.length];
        int[] bestPriceWithSell = new int[prices.length];


        bestPriceWithCD1[0] = Integer.MIN_VALUE;
        bestPriceWithCD2[0] = 0;
        bestPriceWithBuy[0] = -prices[0];
        bestPriceWithSell[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            bestPriceWithBuy[i] = bestPriceWithCD2[i-1] - prices[i];

            bestPriceWithCD2[i] = Math.max(bestPriceWithCD2[i-1], bestPriceWithSell[i-1]);

            int sellAfterBuy = bestPriceWithBuy[i-1] + prices[i];
            int sellAfterCD = bestPriceWithCD1[i-1] + prices[i];

            bestPriceWithSell[i] = Math.max(sellAfterBuy, sellAfterCD);

            bestPriceWithCD1[i] = Math.max(bestPriceWithCD1[i-1], bestPriceWithBuy[i-1]);
        }

        return Math.max(bestPriceWithSell[prices.length-1], bestPriceWithCD2[prices.length-1]);
    }
}
