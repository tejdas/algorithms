package net.lc.dp;

import java.util.Arrays;

/**
 * 322
 * Dynamic Programming
 */
public class CoinChange {
    public int coinChange(int[] coins, int amount) {

        /**
         * One-dimensional DP, for every amount i, stores how many coins were used.
         * dpRes[i] == -1, means we could not make the amount of i
         */
        int[] dpRes = new int[amount+1];
        Arrays.fill(dpRes, Integer.MAX_VALUE);
        dpRes[0] = 0;

        for (int i = 1; i <= amount; i++) {
            int res = Integer.MAX_VALUE;
            /**
             * Try each coin
             */
            for (int j = 0; j < coins.length; j++) {
                int coinval = coins[j];
                if (coinval > i) continue; // not enough amount left

                if (dpRes[i-coinval] == Integer.MAX_VALUE) continue; // could not make i-coinval

                int ret = dpRes[i-coinval] + 1; // add 1, because we used one more coin

                res = Math.min(res, ret);
            }
            dpRes[i] = res;
        }
        return dpRes[amount]==Integer.MAX_VALUE? -1 : dpRes[amount];
    }
}
