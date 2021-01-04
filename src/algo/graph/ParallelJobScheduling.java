package algo.graph;

import java.util.*;

/**
 * Given a set of Jobs with successors and cost of each Job, find the minimum time in which all the Jobs can be finished.
 * Topological Sort
 * DFS
 * Dynamic Programming
 * Longest path in Directed Graph
 * Iterate Jobs in the topological order.
 * Greedy algorithm.
 */
public class ParallelJobScheduling {

    static class Job {
        public Job(final int id, final int cost) {
            super();
            this.id = id;
            this.cost = cost;

            /**
             * Every job is initially treated with having no predecessors, thus,
             * it's earliestFinishTime is set to its cost. The earliestFinishTime
             * is recomputed while traversing the Jobs in the topological order.
             */
            this.earliestFinishTime = cost;
        }
        int id;
        List<Integer> succeeding = new ArrayList<>();
        int cost;
        int earliestFinishTime;

        public void addsucceedingJobs(final int[] array) {
            if (array != null && array.length>0) {
                for (final int val : array) {
                    succeeding.add(val);
                }
            }
        }
    }

    static Map<Integer, Job> jobs = new HashMap<>();
    static Set<Integer> visited = new HashSet<>();

    static void dfs(final Job curjob, final Stack<Integer> topological) {
        visited.add(curjob.id);
        for (final int succ : curjob.succeeding) {
            if (!visited.contains(succ)) {
                dfs(jobs.get(succ), topological);
            }
        }
        topological.push(curjob.id);
    }

    static void computeMinSchedulingTime() {
        final Stack<Integer> topological = new Stack<>();

        for (final Job job : jobs.values()) {
            if (!visited.contains(job.id)) {
                dfs(job, topological);
            }
        }

        /**
         * Reverse topological stack
         */
        final List<Integer> topologicalList = new ArrayList<>(topological);
        Collections.reverse(topologicalList);

        System.out.println("Topological");
        for (final int val : topologicalList) System.out.print(val + " ");
        System.out.println();

        /**
         * Iterate in the topological order
         */
        for (final int jobId : topologicalList) {
            final Job job = jobs.get(jobId);
            for (final int succJobId : job.succeeding) {
                final Job succJob = jobs.get(succJobId);
                /**
                 * Adjust succeeding job's earliest finish time, based on
                 * current job's earliest finish time, and succeeding job's cost.
                 *
                 * A successor Job cannot succeed earlier than current job's finish time + successor job time.
                 */
                if (succJob.earliestFinishTime < job.earliestFinishTime + succJob.cost) {
                    succJob.earliestFinishTime = job.earliestFinishTime + succJob.cost;
                }
            }
        }

        int minTime = 0;
        for (final int jobId : topologicalList) {

            final Job job = jobs.get(jobId);
            minTime = Math.max(minTime, job.earliestFinishTime);
        }

        System.out.println("Min total time to finish all jobs: " + minTime);
    }

    static void addJob(final int jobid, final int cost, final int[] succ) {
        final Job job = new Job(jobid, cost);
        job.addsucceedingJobs(succ);
        jobs.put(jobid,  job);
    }

    public static void main(final String[] args) {
        addJob(0, 41, new int[] {1, 7, 9});
        addJob(1, 51, new int[] {2, 10});
        addJob(2, 50, null);
        addJob(3, 36, new int[] {10});
        addJob(4, 38, new int[] {10});
        addJob(5, 45, null);
        addJob(6, 21, new int[] {3, 8});
        addJob(7, 32, new int[] {3, 8});
        addJob(8, 32, new int[] {2});
        addJob(9, 29, new int[] {4, 6});
        addJob(10, 100, null);

        computeMinSchedulingTime();
    }
}
