package net.lc.greedy;

import java.util.*;

/**
 * 621
 * Greedy
 * Priority-Queue
 */
public class TaskScheduler {
    private final Map<Character, Integer> taskCountMap = new HashMap<>();

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

    private Queue<ParkedTask> parkedTaskList = new LinkedList<>();
    public int leastInterval(char[] tasks, int n) {

        taskCountMap.clear();
        parkedTaskList.clear();

        if (tasks == null || tasks.length == 0) {
            return 0;
        }

        for (char c : tasks) {
            if (taskCountMap.containsKey(c)) {
                taskCountMap.put(c, 1 + taskCountMap.get(c));
            } else {
                taskCountMap.put(c, 1);
            }
        }

        PriorityQueue<Task> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : taskCountMap.entrySet()) {
            pq.add(new Task(entry.getKey(), entry.getValue()));
        }

        int timeTaken = 0;
        while (!pq.isEmpty() || !parkedTaskList.isEmpty()) {
            timeTaken++;

            if (!pq.isEmpty()) {
                /**
                 * Give priority to tasks that have highest remaining frequency.
                 */
                Task task = pq.remove();

                /**
                 * Since the task cannot be immediately executed again, put in parkedTaskList
                 */
                if (task.remainingCount > 1) {
                    parkedTaskList.add(new ParkedTask(task.taskId, task.remainingCount - 1, timeTaken + n));
                }
            }

            /**
             * Tasks are parked in the queue in the order in which they were executed. So, check to see if the head of the queue
             * is ready to execute again (its future execute time is already up). If so, remove from parked queue, and put it back
             * in PQ.
             */
            while (!parkedTaskList.isEmpty()) {
                if (parkedTaskList.peek().futureExecTime <= timeTaken) {
                    ParkedTask pt = parkedTaskList.remove();
                    pq.add(new Task(pt.taskId, pt.remainingCount));
                } else {
                    break;
                }
            }
        }

        return timeTaken;
    }
}
