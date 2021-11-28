package net.lc.interval;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/non-overlapping-intervals/
 * 435
 * Greedy
 * Sort
 */
public class NonOverlappingIntervals {
    static class Interval implements Comparable<Interval> {
        int beginTime;
        int endTime;

        public Interval(int beginTime, int endTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Interval o) {
            if (o.endTime == this.endTime) {
                return Integer.compare(this.beginTime, o.beginTime);
            }

            return Integer.compare(this.endTime, o.endTime);
        }
    }
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <=1 ) return 0;


        Interval[] list = new Interval[intervals.length];
        int index = 0;
        for (int[] i : intervals) {
            list[index++] = new Interval(i[0], i[1]);
        }

        Arrays.sort(list);

        int result = 0;
        Interval prev = list[0];

        for (int i = 1; i < list.length; i++) {
            Interval cur = list[i];
            if (cur.beginTime < prev.endTime) {
                /**
                 * overlaps with prev interval, so cur interval need to be removed.
                 * DO not move prev interval
                 */
                result++;
            } else {
                prev = cur;
            }
        }

        return result;
    }
}
