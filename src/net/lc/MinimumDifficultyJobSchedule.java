package net.lc;

import java.util.Arrays;
import java.util.Stack;

/**
 * 1335
 * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/submissions/
 * Dynamic Programming
 */
public class MinimumDifficultyJobSchedule {
    private int[][] memo = new int[512][16];
    private int[] jobDifficulty;

    public int minDifficulty(int[] jobDifficulty, int d) {

        this.jobDifficulty = jobDifficulty;
        for (int[] memoi : memo) {
            Arrays.fill(memoi, -1);
        }

        memo[0][1] = jobDifficulty[0];
        int res = calc(jobDifficulty.length - 1, d);
        return res;
    }

    /**
     * Two-dimensional DP. (jobIndex and number of days)
     * Calculate minDifficulty with upto jobIndex and dayIndex as follows:
     *
     * Find minDifficulty(j, dayIndex-1) and consider doing all jobs (from j+1 upto jobIndex) on dayIndex
     * Add the two.
     * Keep track of the minimum value.
     * (find the max-difficulty of all jobs between j+1 and jobIndex).
     * @param jobIndex
     * @param days
     * @return
     */
    private int calc(int jobIndex, int days) {
        if (jobIndex < 0)
            return -1;
        if (days < 0)
            return -1;

        if (days > jobIndex + 1)
            return -1;

        if (memo[jobIndex][days] != -1)
            return memo[jobIndex][days];

        if (days == 1) {
            int maxD = 0;
            for (int i = 0; i <= jobIndex; i++) {
                maxD = Math.max(maxD, jobDifficulty[i]);
            }

            memo[jobIndex][days] = maxD;
            return maxD;
        }
        int minD = Integer.MAX_VALUE;

        int max = jobDifficulty[jobIndex];
        for (int j = jobIndex-1; j >= 0; j--) {
            int v = calc(j, days - 1);
            if (v == -1)
                continue;

            int maxV = max;
            minD = Math.min(minD, v + maxV);
            max = Math.max(max, jobDifficulty[j]);
        }

        if (minD == Integer.MAX_VALUE) {
            memo[jobIndex][days] = -1;
        } else {
            memo[jobIndex][days] = minD;
        }
        return memo[jobIndex][days];
    }

    public static void main(String[] args) {
        {
            int[] jobDifficulty = { 6, 5, 4, 3, 2, 1 };
            System.out.println(new MinimumDifficultyJobSchedule().minDifficulty(jobDifficulty, 2));
        }

        {
            int[] jobDifficulty = { 7, 1, 7, 1, 7, 1 };
            System.out.println(new MinimumDifficultyJobSchedule().minDifficulty(jobDifficulty, 3));
        }

        {
            int[] jobDifficulty = { 186, 398, 479, 206, 885, 423, 805, 112, 925, 656, 16, 932, 740, 292, 671, 360 };
            System.out.println(new MinimumDifficultyJobSchedule().minDifficulty(jobDifficulty, 4));
        }

        {
            int[] jobDifficulty = { 11, 111, 22, 222, 33, 333, 44, 444 };
            //int[] jobDifficulty = { 11, 111};
            System.out.println(new MinimumDifficultyJobSchedule().minDifficulty(jobDifficulty, 6));
        }
    }
}
