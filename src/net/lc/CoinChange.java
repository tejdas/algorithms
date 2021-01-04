package net.lc;

import java.util.Arrays;

/**
 * 322
 * Dynamic Programming
 */
public class CoinChange {
    public int coinChange(int[] coins, int amount) {

        /**
         * One-dimensional DP, for every amount
         * dpRes[i] == -1, means we could not make the amount of i
         */
        int[] dpRes = new int[amount+1];
        Arrays.fill(dpRes, 0);

        for (int i = 1; i <= amount; i++) {
            int res = Integer.MAX_VALUE;
            /**
             * Try each coin
             */
            for (int j = 0; j < coins.length; j++) {
                int coinval = coins[j];
                if (coinval > i) continue; // not enough amount left

                if (dpRes[i-coinval] == -1) continue; // could not make i-coinval

                int ret = dpRes[i-coinval] + 1; // add 1, because we used one more coin

                res = Math.min(res, ret);
            }

            if (res == Integer.MAX_VALUE) {
                // we could not make the amount i
                dpRes[i] = -1;
            } else {
                dpRes[i] = res;
            }
        }

        return dpRes[amount];
    }
}
