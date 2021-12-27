package net.lc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 1229
 * Greedy
 * Priority-Queue
 */
public class MeetingScheduler {
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

    /**
     * Sort intervals by beginTime
     * If there is an overlap between two intervals, return the first overlap that is >= duration
     * @param slots1
     * @param slots2
     * @param duration
     * @return
     */
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        PriorityQueue<Interval> pq = new PriorityQueue<>();

        for (int[] slot : slots1) {
            pq.add(new Interval(slot[0], slot[1]));
        }
        for (int[] slot : slots2) {
            pq.add(new Interval(slot[0], slot[1]));
        }

        Interval prev = null;
        while (!pq.isEmpty()) {
            Interval intv = pq.remove();
            if (prev == null) {
                prev = intv;
            } else {
                if (intv.begin >= prev.begin && intv.end <= prev.end) {
                    /**
                     * current interval completely overlaps with previous
                     */
                    if (intv.end - intv.begin >= duration)
                        return Arrays.asList(intv.begin, intv.begin + duration);
                } else if (intv.begin > prev.end) {
                    /**
                     * no overlap, so ignore
                     */
                    prev = intv;
                } else {
                    if (prev.end - intv.begin >= duration) {
                        // there is an overlap. Check if it is >= duration
                        return Arrays.asList(intv.begin, intv.begin + duration);
                    }
                    prev = intv;
                }
            }
        }
        return Collections.emptyList();
    }


    public static void main(String[] args) {
        {
            int[][] slots1 = { { 10, 50 }, { 60, 120 }, { 140, 210 } };
            int[][] slots2 = { { 0, 15 }, { 60, 70 } };
            List<Integer> res = new MeetingScheduler().minAvailableDuration(slots1, slots2, 8);
            if (res != null) {
                System.out.println(Arrays.toString(res.toArray(new Integer[res.size()])));
            }
        }
        {
            int[][] slots1 = { { 10, 50 }, { 60, 120 }, { 140, 210 } };
            int[][] slots2 = { { 0, 15 }, { 60, 70 } };
            List<Integer> res = new MeetingScheduler().minAvailableDuration(slots1, slots2, 12);
            if (res != null) {
                System.out.println(Arrays.toString(res.toArray(new Integer[res.size()])));
            }
        }
    }
}
