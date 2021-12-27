package net.lc.interval;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 56
 */
public class MergeIntervals {
    static class Interval {
        int start;
        int end;

        public Interval() {
        }

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

            if (time == o.time) {
                if (this.isBegin == o.isBegin) {
                    return 0;
                } else if (this.isBegin) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return Integer.compare(time, o.time);
        }
    }

    public List<Interval> merge(List<Interval> schedule) {
        PriorityQueue<Boundary> pq = new PriorityQueue<>();

        for (Interval interval : schedule) {
            pq.add(new Boundary(interval.start, true));
            pq.add(new Boundary(interval.end, false));
        }

        if (pq.isEmpty()) {
            return new ArrayList<>();
        }

        List<Interval> output = new ArrayList<>();

        int lastBegin = -1;
        int depth = 0;

        while (!pq.isEmpty()) {
            Boundary b = pq.poll();
            if (b.isBegin) {
                if (depth == 0) {
                    lastBegin = b.time;
                }
                depth++;
            } else {
                depth--;
                if (depth == 0) {
                    output.add(new Interval(lastBegin, b.time));
                }
            }
        }

        return output;
    }
}
