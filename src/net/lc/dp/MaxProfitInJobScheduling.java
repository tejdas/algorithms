package net.lc.dp;

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
        Arrays.sort(jobs);


        final int[] profits = new int[jobs.length];
        int maxProfit = 0;

        SortedSet<Profits> profitSet = new TreeSet<>();

        for (int i = 0; i < jobs.length; i++) {
            Iterator<Profits> iter = profitSet.iterator();
            profits[i] = jobs[i].profit;
            while (iter.hasNext()) {
                Profits p = iter.next();

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
