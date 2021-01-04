package net.lc;

import java.util.Arrays;

/**
 * 518
 * Dynamic Programming
 */
public class CoinChange2 {
    private int[][] dpRes;

    public int change(int amount, int[] coins) {
        /**
         * Two-dimensional DP
         * Total amount
         * Range of coin-types (i means only coin indices 0 thru i were used)
         */
        dpRes = new int[amount + 1][coins.length];
        for (int[] d : dpRes) {
            Arrays.fill(d, -1);
        }
        Arrays.fill(dpRes[0], 1);

        numberOfCombinations(amount, coins, coins.length - 1);
        return dpRes[amount][coins.length - 1];
    }

    private int numberOfCombinations(int amount, int[] coins, int coinRange) {
        if (dpRes[amount][coinRange] != -1)
            return dpRes[amount][coinRange];

        int res = 0;
        /**
         * Scan thru all the coins in the coinRange.
         * Also, recursively vary/limit the coin-range.
         */
        for (int idx = coinRange; idx >= 0; idx--) {
            int coinval = coins[idx];
            if (amount - coinval < 0)
                continue;

            res += numberOfCombinations(amount - coinval, coins, idx);
        }

        dpRes[amount][coinRange] = res;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new CoinChange2().change(5, new int[] { 1, 2, 5 }));
        System.out.println(new CoinChange2().change(3, new int[] { 2 }));
    }
}
