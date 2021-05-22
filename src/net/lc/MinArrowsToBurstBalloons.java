package net.lc;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/submissions/
 * 452
 * Greedy
 * Sorting
 *
 */
public class MinArrowsToBurstBalloons {
    static class Interval implements Comparable<Interval> {
        int start;
        int end;

        public Interval(int beginTime, int endTime) {
            this.start = beginTime;
            this.end = endTime;
        }

        @Override
        public int compareTo(Interval o) {
            if (o.end == this.end) {
                return Integer.compare(this.start, o.start);
            }

            return Integer.compare(this.end, o.end);
        }
    }

    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length <1 ) return 0;

        if (points.length == 1) return 1;


        Interval[] list = new Interval[points.length];
        int index = 0;
        for (int[] i : points) {
            list[index++] = new Interval(i[0], i[1]);
        }

        Arrays.sort(list);

        int lastLocation = Integer.MIN_VALUE;
        int hits = 0;

        for (Interval interval : list) {
            if (interval.start > lastLocation) {
                lastLocation = interval.end;
                hits++;
            }
        }
        return hits;
    }

    public static void main(String[] args) {
        System.out.println(new MinArrowsToBurstBalloons().findMinArrowShots(new int[][] {{10,16}, {2,8}, {1,6}, {7,12}}));
        System.out.println(new MinArrowsToBurstBalloons().findMinArrowShots(new int[][] {{1,2},{3,4},{5,6},{7,8}}));
        System.out.println(new MinArrowsToBurstBalloons().findMinArrowShots(new int[][] {{1,2},{2,3}, {3,4},{4,5}}));
    }
}
