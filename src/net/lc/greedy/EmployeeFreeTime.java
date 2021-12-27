package net.lc.greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 759
 * Interval
 * Priority-Queue
 */
public class EmployeeFreeTime {
    static class Interval {
        int start;
        int end;
        public Interval(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public String toString() {
            return "Interval{" + "start=" + start + ", end=" + end + '}';
        }
    }

    static class Boundary implements Comparable<Boundary> {
        int time;
        boolean isBegin;

        public Boundary(int time, boolean isBegin) {
            this.time = time;
            this.isBegin = isBegin;
        }

        @Override
        public int compareTo(Boundary o) {
            return Integer.compare(time, o.time);
        }
    }

    /**
     * Instead of using Interval, we need to use Boundary here. Because there are multiple employees, and their
     * interviews could overlap in a nested manner.
     *
     * So we will use boundaries. We will keep track of nesting depth. For Begin, increment the depth.
     * For End, decrement the depth.
     * When the depth is 0, we can record the time, and then use it when a new Begin starts after that.
     * @param schedule
     * @return
     */
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        PriorityQueue<Boundary> pq = new PriorityQueue<>();

        for (List<Interval> eschedule : schedule) {
            for (Interval interval : eschedule) {
                pq.add(new Boundary(interval.start, true));
                pq.add(new Boundary(interval.end, false));
            }
        }

        if (pq.isEmpty()) {
            return new ArrayList<>();
        }

        List<Interval> output = new ArrayList<>();

        int lastEnd = -1;
        int depth = 0;

        while (!pq.isEmpty()) {
            Boundary b = pq.poll();
            if (b.isBegin) {
                if (depth == 0) {
                    if (lastEnd != -1) {
                        /**
                         * do not consider -infinite to b.time as a boundary, because this is open-ended.
                         */
                        if (lastEnd < b.time) {
                            output.add(new Interval(lastEnd, b.time));
                        }
                    }
                }
                depth++;
            } else {
                depth--;
                if (depth == 0) {
                    // end of overlapped intervals, so record this time to be used for beginning of another free interval.
                    lastEnd = b.time;
                }
            }
        }
        return output;
    }
}
