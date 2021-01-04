package net.lc;

import java.util.*;

public class TaskSchedulerII {
    private final int[] taskCountMap = new int[26];

    static class Task implements Comparable<Task> {
        private final char taskId;
        private final int remainingCount;

        public Task(char taskId, int remainingCount) {
            this.taskId = taskId;
            this.remainingCount = remainingCount;
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.remainingCount, this.remainingCount);
        }
    }

    static class ParkedTask {
        char taskId;
        int remainingCount;
        int futureExecTime;

        public ParkedTask(char taskId, int remainingCount, int futureExecTime) {
            this.taskId = taskId;
            this.remainingCount = remainingCount;
            this.futureExecTime = futureExecTime;
        }
    }

    private Queue<ParkedTask> parkedTaskQ = new LinkedList<>();

    /**
     * Remove the tasks from PQ in the order of remaining counts (higher-to-lower).
     * Execute the task. Then put it in the ParkedQueue, until it is ready to execute
     * again, and bring it back to PQ.
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {

        if (tasks == null || tasks.length == 0) {
            return 0;
        }

        for (char c : tasks) {
            taskCountMap[c - 'A']++;
        }

        PriorityQueue<Task> pq = new PriorityQueue<>();
        for (int i = 0; i < taskCountMap.length; i++) {
            if (taskCountMap[i] > 0) {
                char key = (char) (i + 'A');
                pq.add(new Task(key, taskCountMap[i]));
            }
        }

        int ticks = 0;
        while (!pq.isEmpty() || !parkedTaskQ.isEmpty()) {
            ticks++;

            if (!pq.isEmpty()) {
                Task task = pq.remove();
                System.out.println("executing: at tick: " + ticks + "    taskId: " + task.taskId);

                if (task.remainingCount > 1) {
                    parkedTaskQ.add(new ParkedTask(task.taskId, task.remainingCount - 1, ticks + n));
                }
            }

            while (!parkedTaskQ.isEmpty()) {
                if (parkedTaskQ.peek().futureExecTime <= ticks) {
                    ParkedTask pt = parkedTaskQ.remove();
                    pq.add(new Task(pt.taskId, pt.remainingCount));
                } else {
                    break;
                }
            }
        }

        return ticks;
    }

    public static void main(String[] args) {
        {
            char[] tasks = { 'A', 'A', 'A', 'B', 'B', 'B' };
            System.out.println(new TaskSchedulerII().leastInterval(tasks, 2));
        }

        {
            char[] tasks = { 'A', 'A' };
            //System.out.println(new TaskSchedulerII().leastInterval(tasks, 2));
        }
        {
            char[] tasks = { 'A', 'A', 'A', 'B', 'B', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
            System.out.println(new TaskSchedulerII().leastInterval(tasks, 7));
        }
    }
}
