package net.lc.greedy;

import java.util.*;

/**
 * 1235
 * Dynamic Programming
 * Greedy
 */
public class MaxProfitInJobScheduling {
    static class Job implements Comparable<Job> {
        public Job(final int start, final int end, final int profit) {
            super();
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
        int start;
        int end;
        int profit;

        @Override
        public int compareTo(final Job o) {
            return Integer.compare(this.end, o.end);
        }
    }

    static class Profits implements Comparable<Profits> {
        int profit;
        int index;

        public Profits(int profit, int index) {
            this.profit = profit;
            this.index = index;
        }

        @Override
        public int compareTo(Profits o) {
            return Integer.compare(o.profit, this.profit);
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profitArray) {

        List<Job> list = new ArrayList<>();
        for (int i = 0; i < startTime.length; i++) {
            list.add(new Job(startTime[i], endTime[i], profitArray[i]));
        }

        final Job[] jobs = list.toArray(new Job[list.size()]);
        /**
         * sort jobs by end time
         */
        Arrays.sort(jobs);

        /**
         * maxProfit at job[i]: DP memoization
         */
        final int[] profits = new int[jobs.length];
        int maxProfit = 0;

        /**
         * Maintain a sorted set of jobs that have already been picked up, sorted by profit (max at top).
         * Initially, it was a m*n iteration, where for the current job, we used to check which of the earlier
         * jobs picked (between 0 and i-1) gave the max profit, and choose that.
         *
         * But we improved on that, and now storing the past results in a sorted set, so that we will always choose
         * the job ending max profit.
         */
        SortedSet<Profits> profitSet = new TreeSet<>();

        for (int i = 0; i < jobs.length; i++) {
            Iterator<Profits> iter = profitSet.iterator();
            profits[i] = jobs[i].profit;
            while (iter.hasNext()) {
                Profits p = iter.next();

                /**
                 * Pick up the past job with highest profit and check to see if current job can be started after that.
                 */
                if (jobs[i].start >= jobs[p.index].end) {
                    profits[i] = jobs[i].profit + p.profit;
                    break;
                }
            }

            maxProfit = Math.max(maxProfit, profits[i]);
            profitSet.add(new Profits(profits[i], i));
        }
        return maxProfit;
    }
}
