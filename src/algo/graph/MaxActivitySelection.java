package algo.graph;

import java.util.PriorityQueue;

/**
 * Given a set of activities with <startTime, endTime>, select the maximum number of activities
 * that could be scheduled, without overlapping.
 *
 */
public class MaxActivitySelection {

    static class Activity implements Comparable<Activity> {
        public Activity(final int id, final int start, final int end) {
            super();
            this.id = id;
            this.start = start;
            this.end = end;
        }

        int id;
        int start;
        int end;

        @Override
        public int compareTo(final Activity o) {
            return Integer.compare(this.end,  o.end);
        }
    }

    /**
     * Find a way that max activities can be chosen, and the order in which they will be
     * processed.
     *
     * Iterate the activities in the order at which it ends. Select an activity if it starts
     * AFTER the previous activity.
     *
     * @param pq
     */
    static void selectMaxActivities(final PriorityQueue<Activity> pq) {

        Activity prev = null;
        while (!pq.isEmpty()) {
            final Activity activity = pq.poll();
            if (prev == null) {
                System.out.println("id: " + activity.id + " start: " + activity.start + " end: " + activity.end);
                prev = activity;
            } else if (activity.start >= prev.end) {
                    System.out.println("id: " + activity.id + " start: " + activity.start + " end: " + activity.end);
                    prev = activity;
            }
        }
    }
    public static void main(final String[] args) {
        /*
         * Sort the activities by end-time.
         */
        final PriorityQueue<Activity> pq = new PriorityQueue<>();

        pq.add(new Activity(0, 1, 2));
        pq.add(new Activity(2, 0, 6));
        pq.add(new Activity(4, 8, 9));
        pq.add(new Activity(1, 3, 4));
        pq.add(new Activity(5, 5, 9));
        pq.add(new Activity(3, 5, 7));

        selectMaxActivities(pq);
    }

}
