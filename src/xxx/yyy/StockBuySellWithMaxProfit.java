package xxx.yyy;

import java.util.Arrays;
import java.util.Random;


public class StockBuySellWithMaxProfit {

    static int maximizeProfit(final int[] stock) {
        int maxProfit = 0;
        int lastMin = Integer.MAX_VALUE;
        int sellingPrice = -1;
        int buyingPrice = -1;

        for (final int val : stock) {
            if (val < lastMin) {
                lastMin = val;
            } else {
                final int profit = val - lastMin;
                if (profit > maxProfit) {
                    maxProfit = profit;
                    sellingPrice = val;
                    buyingPrice = lastMin;
                }
            }
        }

        System.out.println("Buy: " + buyingPrice + "  Sell: " + sellingPrice + " maxProfit: " + maxProfit);
        return maxProfit;
    }

    public static void main(final String[] args) {
        final int[] stock = Util.generateRandomNumbers(new Random(), 10);
        System.out.println(Arrays.toString(stock));
        maximizeProfit(stock);
    }

}
