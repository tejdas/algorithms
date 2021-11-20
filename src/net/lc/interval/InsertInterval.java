package net.lc.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 57
 */
public class InsertInterval {
    static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public Interval() {
        }
    }
    private static class IEdge implements Comparable<IEdge> {
        int val;
        int edge; // 0 start, 1 end

        public IEdge(int val, int edge) {
            this.val = val;
            this.edge = edge;
        }

        @Override
        public int compareTo(IEdge o) {
            if (this.val == o.val) {
                return Integer.compare(this.edge, o.edge);
            }

            return Integer.compare(this.val, o.val);
        }
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> output = new ArrayList<>();

        IEdge[] array = new IEdge[intervals.size() * 2 + 2];

        int index = 0;
        for (Interval interval : intervals) {
            array[index++] = new IEdge(interval.start, 0);
            array[index++] = new IEdge(interval.end, 1);
        }

        array[index++] = new IEdge(newInterval.start, 0);
        array[index++] = new IEdge(newInterval.end, 1);

        Arrays.sort(array);

        int start = 0;

        int overlap = 0;
        for (IEdge edge : array) {
            if (edge.edge == 0) {
                // encountered a start edge
                if (overlap == 0) {
                    // beginning of a new overlapped interval; initialize start
                    start = edge.val;
                }
                overlap++;
            } else {
                // encountered an end edge.
                overlap--;
                if (overlap == 0) {
                    // end of an  overlapped interval
                    output.add(new Interval(start, edge.val));
                }
            }
        }
        return output;
    }
}
