package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Dynamic Programming.
 *
 * Given a set of jobs, with <startTime, endTime, profit>, find the job-series that would lead to maximum profit.
 */
public class WeightedJobScheduling {

    static class Job implements Comparable<Job> {
        public Job(final int id, final int start, final int end, final int profit) {
            super();
            this.id = id;
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
        int id;
        int start;
        int end;
        int profit;

        @Override
        public int compareTo(final Job o) {
            return Integer.compare(this.end, o.end);
        }
    }

    static void weightedJobSchedule(final List<Job> input) {
        /**
         * Sort the jobs in the order of end time.
         */
        final Job[] jobs = input.toArray(new Job[input.size()]);
        Arrays.sort(jobs);

        final int[] profits = new int[jobs.length];

        final Map<Integer, Set<Integer>> jobSequence = new HashMap<>();

        for (int i = 0; i < jobs.length; i++) {
            jobSequence.put(jobs[i].id, new LinkedHashSet<>());
        }

        /**
         * bottom-up Dynamic Programming.
         */
        for (int i = 0; i < jobs.length; i++) {

            final int id = jobs[i].id;
            int maxProfit = jobs[i].profit;

            /**
             * Find a previous job which ends before current job start
             * and which results in more profit
             */
            int prevJobIdWithMaxProfit = -1;
            for (int j = 0; j < i; j++) {

                if (jobs[j].end <= jobs[i].start) {
                    if (profits[j] + jobs[i].profit > maxProfit) {
                        maxProfit = profits[j] + jobs[i].profit;
                        prevJobIdWithMaxProfit = j;
                    }
                }
            }

            profits[i] = maxProfit;

            if (prevJobIdWithMaxProfit != -1) {
                jobSequence.get(id).clear();
                jobSequence.get(id).addAll(jobSequence.get(jobs[prevJobIdWithMaxProfit].id));
            }
            jobSequence.get(id).add(i);
        }

        for (int i = 0; i < profits.length; i++) {
            System.out.print("Ending in job: " + i + "   Profit: " + profits[i] + "  jobs: ");
            final Set<Integer> seq = jobSequence.get(i);
            for (final int j : seq) {
                System.out.print("  " + j);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(final String[] args) {
        final List<Job> input = new ArrayList<>();
/*
        input.add(new Job(0, 1, 2, 50));
        input.add(new Job(1, 3, 5, 20));
        input.add(new Job(2, 6, 19, 100));
        input.add(new Job(3, 2, 100, 200));
        */

        input.add(new Job(0, 1, 3, 5));
        input.add(new Job(1, 2, 5, 6));
        input.add(new Job(2, 4, 6, 5));
        input.add(new Job(3, 6, 7, 4));
        input.add(new Job(4, 5, 8, 11));
        input.add(new Job(5, 7, 9, 2));

        weightedJobSchedule(input);
    }
}
