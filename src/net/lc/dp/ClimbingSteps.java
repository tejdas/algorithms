package net.lc.dp;

/**
 * 70
 * bottom-up
 * Dynamic Programming
 */
public class ClimbingSteps {
    static int[] allowedSteps = {1, 2};
    public int climbStairs(int numSteps) {
        int[] memo = new int[numSteps+1];
        memo[0] = 1;

        for (int i = 1; i <= numSteps; i++) {
            int totalDistinctWays = 0;

            // memo[i] = sum of memo[i-allowedStep] for all allowed steps.
            for (int allowedStep : allowedSteps) {
                if (i - allowedStep >= 0) {
                    totalDistinctWays += memo[i - allowedStep];
                }
            }

            memo[i] = totalDistinctWays;
        }
        return memo[numSteps];
    }
}
