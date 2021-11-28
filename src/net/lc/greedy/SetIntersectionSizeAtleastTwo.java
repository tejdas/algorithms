package net.lc.greedy;

import java.util.*;

/**
 * 757
 * Greedy
 * Intervals
 */
public class SetIntersectionSizeAtleastTwo {

    static class Interval implements Comparable<Interval> {
        int begin;
        int end;

        public Interval(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            /**
             * pick up the interval that ends first.
             * If two intervals end at the same time, pick up the one that begins last.
             */
            if (this.end != o.end) {
                return Integer.compare(this.end, o.end);
            }
            return Integer.compare(o.begin, this.begin);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Interval{");
            sb.append("begin=").append(begin);
            sb.append(", end=").append(end);
            sb.append('}');
            return sb.toString();
        }
    }

    public int intersectionSizeTwo(int[][] intervals) {

        if (intervals == null || intervals.length == 0) return 0;
        PriorityQueue<Interval> pq = new PriorityQueue<>();
        for (int[] intv : intervals) {
            pq.add(new Interval(intv[0], intv[1]));
        }

        List<Integer> result = new ArrayList<>();

        Interval intv = pq.poll();

        // add last two numbers of first interval
        // keep track of two numbers: prev and cur
        result.add(intv.end-1);
        result.add(intv.end);

        int cur = intv.end;
        int prev = intv.end-1;

        while (!pq.isEmpty()) {
            intv = pq.poll();

            if (intv.begin > cur) {
                /**
                 * New interval begins after cur, so no overlap.
                 * Add last two numbers.
                 */
                result.add(intv.end-1);
                result.add(intv.end);

                prev = intv.end-1;
                cur = intv.end;
                continue;
            }

            /**
             * new interval begins at cur. So, consider that added.
             * Add the last number.
             * Keep track of first and last number of current interval
             * as prev and cur. The reason being: there could potentially be
             * a subsequent interval that begins before current interval, so that will be covered
             * as well.
             */
            if (intv.begin == cur) {
                result.add(intv.end);
                prev = cur;
                cur = intv.end;
                continue;
            }

            /**
             * new interval's begin is between prev and cur.
             * In that case, just add last number.
             */
            if (intv.begin > prev && intv.begin < cur) {
                result.add(intv.end);
                prev = cur;
                cur = intv.end;
                continue;
            }

            /**
             * new interval completely overlaps prev and cur.
             * No need to do anything.
             */
            if (intv.begin <= prev && intv.end >= cur) {
                continue;
            }
        }

        System.out.println(Arrays.toString(result.toArray()));
        return result.size();
    }

    public static void main(String[] args) {


        {
            int[][] intervals = {{1,3},{1,4},{2,5},{3,5}};
            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }
        {
            int[][] intervals = {{1,2},{2,3},{2,4},{4,5}};
            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }
        {
            int[][] intervals = {{1,3},{3,7},{5,7},{7,8}};
            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }
        {
            int[][] intervals = {{3,7},{7,8},{2,10},{4,11},{7,11},{6,12},{11,12},{3,15},{7,15},{6,16}};
            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }

        {
            // expected 4
            int[][] intervals = {{11,24},{12,25},{2,8},{14,25},{10,23}};
            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }

        {
            int[][] intervals = {{33,44},{42,43},{13,37},{24,33},{24,33},{25,48},{10,47},{18,24},{29,37},{7,34}};

            System.out.println(new SetIntersectionSizeAtleastTwo().intersectionSizeTwo(intervals));
        }
    }
}
