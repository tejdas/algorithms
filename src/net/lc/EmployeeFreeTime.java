package net.lc;

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
        public Interval() {}
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
                        if (lastEnd < b.time) {
                            output.add(new Interval(lastEnd, b.time));
                        }
                    }
                }
                depth++;
            } else {
                depth--;
                if (depth == 0) {
                    lastEnd = b.time;
                }
            }
        }
        return output;
    }
}
