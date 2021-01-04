package net.lc;

import java.util.PriorityQueue;

/**
 * 1288
 * Greedy
 * Priority-Queue
 */
public class RemoveCoveredIntervals {
    static class Interval implements Comparable<Interval> {
        int begin;
        int end;

        public Interval(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.begin == o.begin) {
                return Integer.compare(o.end, this.end);
            }
            return Integer.compare(this.begin, o.begin);
        }
    }
    public int removeCoveredIntervals(int[][] intervals) {

        PriorityQueue<Interval> pq = new PriorityQueue<>();
        for (int[] intv : intervals) {
            pq.add(new Interval(intv[0], intv[1]));
        }

        Interval prev = null;
        int count = 0;
        while (!pq.isEmpty()) {
            Interval intv = pq.remove();
            if (prev == null) {
                prev = intv;
            } else {
                if (intv.begin >= prev.begin && intv.end <= prev.end) {
                    count++;
                } else if (intv.begin > prev.end) {
                    prev = intv;
                } else {
                    prev = intv;
                }
            }
        }
        return intervals.length - count;
    }

    public static void main(String[] args) {
        {
            int[][] intervals = { { 1, 4 }, { 3, 6 }, { 2, 8 } };
            System.out.println(new RemoveCoveredIntervals().removeCoveredIntervals(intervals));
        }
        {
            int[][] intervals = { {3,10},{4,10},{5,11} };
            System.out.println(new RemoveCoveredIntervals().removeCoveredIntervals(intervals));
        }
        {
            int[][] intervals = { { 1, 2 }, { 1,4 }, { 3,4 } };
            System.out.println(new RemoveCoveredIntervals().removeCoveredIntervals(intervals));
        }
        {
            int[][] intervals = { {0,10},{5,12} };
            System.out.println(new RemoveCoveredIntervals().removeCoveredIntervals(intervals));
        }
        {
            int[][] intervals = { { 1, 4 }, { 2,3 } };
            System.out.println(new RemoveCoveredIntervals().removeCoveredIntervals(intervals));
        }
    }
}
