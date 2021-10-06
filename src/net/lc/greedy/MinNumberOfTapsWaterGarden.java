package net.lc.greedy;

import java.util.*;

public class MinNumberOfTapsWaterGarden {

    static class Range implements Comparable<Range> {
        int curIndex;
        int leftEdge;
        int rightEdge;

        public Range(int curIndex, int leftEdge, int rightEdge) {
            this.curIndex = curIndex;
            this.leftEdge = leftEdge;
            this.rightEdge = rightEdge;
        }

        @Override
        public int compareTo(Range o) {
            if (this.leftEdge == o.leftEdge) {
                return Integer.compare(o.rightEdge, this.rightEdge);
            }
            return Integer.compare(this.leftEdge, o.leftEdge);
        }
    }

    public int minTaps(int n, int[] ranges) {

        if (n == 0)
            return 0;

        SortedSet<Range> pq = new TreeSet<>();

        for (int i = 0; i < ranges.length; i++) {
            int range = ranges[i];
            if (range > 0) {
                int leftEdge = Math.max(0, i - range);
                int rightEdge = Math.min(n, i + range);
                pq.add(new Range(i, leftEdge, rightEdge));
            }
        }

        int curRight = -1;
        int potentialRightEdge = -1;
        int potentialIndex = -1;

        int taps = 0;


        Iterator<Range> iter = pq.iterator();
        while (iter.hasNext()) {
            Range r = iter.next();
            if (curRight == -1) {
                if (r.leftEdge > 0)
                    return -1;

                taps++;

                curRight = r.rightEdge;
                potentialRightEdge = curRight;
                if (curRight == n)
                    return taps;
            } else {
                if (r.rightEdge <= curRight) continue;

                if (r.leftEdge <= curRight) {
                    if (r.rightEdge > potentialRightEdge) {
                        potentialRightEdge = r.rightEdge;
                        potentialIndex = r.curIndex;
                    }
                    continue;
                }

                // r.leftEdge > curRight
                if (potentialIndex != -1 && potentialRightEdge >= r.leftEdge) {
                    curRight = potentialRightEdge;
                    potentialIndex = -1;
                    taps++;
                    if (curRight >= n)
                        return taps;

                    if (r.rightEdge > potentialRightEdge) {
                        potentialIndex = r.curIndex;
                        potentialRightEdge = r.rightEdge;
                    }
                } else {
                    return -1;
                }
            }
        }

        if (potentialRightEdge >= n) {
            return taps+1;
        }

        if (curRight < n) {
            return -1;
        }
        return taps;
    }

    public static void main(String[] args) {
/*
        {
            int[] ranges = { 3, 4, 1, 1, 0, 0 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(5, ranges));
        }

        {
            int[] ranges = { 1, 2, 1, 0, 2, 1, 0, 1 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(7, ranges));
        }


        {
            int[] ranges = { 4, 0, 0, 0, 0, 0, 0, 0, 4 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(8, ranges));
        }

        {
            int[] ranges = { 4, 0, 0, 0, 4, 0, 0, 0, 4 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(8, ranges));
        }

        {
            int[] ranges = { 0, 5, 0, 3, 3, 3, 1, 4, 0, 4 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(9, ranges));
        }
*/
        {
            int[] ranges = { 0, 3, 3, 2, 2, 4, 2, 1, 5, 1, 0, 1, 2, 3, 0, 3, 1, 1 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(17, ranges));
        }


        {
            int[] ranges = {1,0,4,0,4,1,4,3,1,1,1,2,1,4,0,3,0,3,0,3,0,5,3,0,0,1,2,1,2,4,3,0,1,0,5,2};
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(35, ranges));
        }

        {
            int[] ranges = {4,1,5,0,5,3,3,3,0,0,3,3,2,2,4,4,2,3,4,2};
            //System.out.println(new MinNumberOfTapsWaterGarden().minTaps(19, ranges));
        }

    }
}
