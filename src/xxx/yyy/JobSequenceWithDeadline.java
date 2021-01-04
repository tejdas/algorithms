package xxx.yyy;

import java.util.*;

public class JobSequenceWithDeadline {
    static class Task implements Comparable<Task> {
        int taskId;
        int deadline;
        int profit;

        public Task(int taskId, int deadline, int profit) {
            this.taskId = taskId;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public int compareTo(Task o) {
            if (this.profit == o.profit) {
                return Integer.compare(this.deadline, o.deadline);
            }
            return Integer.compare(o.profit, this.profit);
        }

        @Override
        public String toString() {
            return "Task{" + "taskId=" + taskId + ", deadline=" + deadline + ", profit=" + profit + '}';
        }
    }

    static class TaskId implements Comparable<TaskId> {
        int taskId;
        int deadline;
        int executeTime;

        public TaskId(int taskId, int deadline, int executeTime) {
            this.taskId = taskId;
            this.deadline = deadline;
            this.executeTime = executeTime;
        }

        @Override
        public int compareTo(TaskId o) {
            return Integer.compare(o.deadline, this.deadline);
        }

        @Override
        public String toString() {
            return "TaskId{" + "taskId=" + taskId + ", deadline=" + deadline + ", executeTime=" + executeTime + '}';
        }
    }

    public List<Task> calcMaxProfit(List<Task> input) {
        List<Task> output = new ArrayList<>();

        PriorityQueue<Task> pq = new PriorityQueue<>();

        for (Task t : input) {
            pq.add(t);
        }

        int executeTime = 0;
        PriorityQueue<TaskId> taskIdPQ = new PriorityQueue<>();
        while (!pq.isEmpty()) {
            Task task = pq.remove();
            if (task.deadline >= executeTime+1) {
                executeTime++;
                output.add(task);
                taskIdPQ.add(new TaskId(task.taskId, task.deadline, executeTime));
            } else {
                System.out.println("trying to choose: " + task);
                List<TaskId> parkedTasks = new ArrayList<>();
                while (!taskIdPQ.isEmpty()) {
                    TaskId selectedTaskId = taskIdPQ.remove();
                    if ((selectedTaskId.deadline >= executeTime+1) && (task.deadline >= selectedTaskId.executeTime)) {
                        System.out.println("matched with: " + selectedTaskId);
                        executeTime++;
                        taskIdPQ.remove(selectedTaskId);
                        taskIdPQ.add(new TaskId(task.taskId, task.deadline, selectedTaskId.executeTime));
                        selectedTaskId.executeTime = executeTime;
                        taskIdPQ.add(selectedTaskId);
                        output.add(task);
                        break;
                    } else {
                        System.out.println("no match yet with: " + selectedTaskId);
                        parkedTasks.add(selectedTaskId);
                    }
                }
                for (TaskId parkedTask : parkedTasks) {
                    taskIdPQ.add(parkedTask);
                }
            }
        }

        while (!taskIdPQ.isEmpty()) {
            System.out.println(taskIdPQ.remove());
        }
        System.out.println("------------------------------");
        return output;
    }

    public static void main(String[] args) {
        List<Task> input = new ArrayList<>();
        input.add(new Task(1, 9, 15));
        input.add(new Task(2, 2, 2));
        input.add(new Task(3, 5, 18));
        input.add(new Task(4, 7, 1));
        input.add(new Task(5, 4, 25));
        input.add(new Task(6, 2, 20));
        input.add(new Task(7, 5, 8));
        input.add(new Task(8, 7, 10));
        input.add(new Task(9, 4, 12));
        input.add(new Task(10, 3, 5));

        List<Task> output = new JobSequenceWithDeadline().calcMaxProfit(input);
        for (Task t : output) {
            System.out.println(t);
        }
    }
}
