package net.lc;

import java.util.PriorityQueue;

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

    public int minTaps2(int n, int[] ranges) {

        if (n == 0)
            return 0;

        PriorityQueue<Range> pq = new PriorityQueue<>();
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

        int taps = 0;

        while (!pq.isEmpty()) {
            Range r = pq.remove();
            if (curRight == -1) {
                if (r.leftEdge > 0)
                    return -1;

                //System.out.println("Choosing: " + r.curIndex);
                taps++;

                curRight = r.rightEdge;
                potentialRightEdge = curRight;
                if (curRight == n)
                    return taps;
            } else {
                if (r.leftEdge > curRight) {
                    if (potentialRightEdge > curRight) {
                        if (r.rightEdge < potentialRightEdge)
                            continue;

                        //System.out.println("Choosing: " + r.curIndex);
                        taps++;

                        curRight = r.rightEdge;
                        potentialRightEdge = curRight;

                        if (curRight == n) {
                            //System.out.println("Adding tap: " + r.curIndex);
                            return taps + 1;
                        }
                    } else {
                        return -1;
                    }
                }
                if (r.rightEdge <= curRight)
                    continue;

                potentialRightEdge = Math.max(potentialRightEdge, r.rightEdge);
                if (potentialRightEdge == n) {
                    //System.out.println("Adding tap: " + r.curIndex);
                    return taps + 1;
                }
            }
        }

        if (curRight < n)
            return -1;
        return taps;
    }

    public int minTaps1(int n, int[] ranges) {

        if (n == 0)
            return 0;

        PriorityQueue<Range> pq = new PriorityQueue<>();
        for (int i = 0; i < ranges.length; i++) {
            int range = ranges[i];
            if (range > 0) {
                int leftEdge = Math.max(0, i - range);
                int rightEdge = Math.min(n, i + range);
                pq.add(new Range(i, leftEdge, rightEdge));
            }
        }

        int curRight = -1;
        int curIndex = -1;
        int coveredIndex = -1;
        int potentialRightEdge = -1;
        int potentialIndex = -1;

        int taps = 0;

        while (!pq.isEmpty()) {
            Range r = pq.remove();
            if (curRight == -1) {
                if (r.leftEdge > 0)
                    return -1;

                taps++;

                curRight = r.rightEdge;
                curIndex = r.curIndex;
                potentialRightEdge = curRight;
                potentialIndex = curIndex;
                if (curRight == n)
                    return taps;
            } else {
                if (r.rightEdge <= curRight) continue;

                if (r.curIndex <= curRight) {
                    if (r.rightEdge > potentialRightEdge) {
                        potentialRightEdge = r.rightEdge;
                        potentialIndex = r.curIndex;
                    }
                    continue;
                }

                // time to choose
                coveredIndex = curRight;

                if (potentialRightEdge > r.rightEdge) {
                    curRight = potentialRightEdge;
                    curIndex = potentialIndex;
                } else {
                    curRight = r.rightEdge;
                    curIndex = r.curIndex;
                }

                if (r.leftEdge > curRight) return -1;

                taps++;
                if (curRight == n) return taps;

            }
        }

        if (curRight < n) {
            System.out.println("returning here");
            return -1;
        }
        return taps;
    }

    public int minTaps(int n, int[] ranges) {

        if (n == 0)
            return 0;

        PriorityQueue<Range> pq = new PriorityQueue<>();
        for (int i = 0; i < ranges.length; i++) {
            int range = ranges[i];
            if (range > 0) {
                int leftEdge = Math.max(0, i - range);
                int rightEdge = Math.min(n, i + range);
                pq.add(new Range(i, leftEdge, rightEdge));
            }
        }

        int curRight = -1;
        int curIndex = -1;
        int potentialRightEdge = -1;

        int taps = 0;

        while (!pq.isEmpty()) {
            Range r = pq.remove();
            if (curRight == -1) {
                if (r.leftEdge > 0)
                    return -1;

                taps++;
                //System.out.println("1 chose tap at: " + r.curIndex + " range: " + ranges[r.curIndex]);

                curRight = r.rightEdge;
                curIndex = r.curIndex;
                potentialRightEdge = curRight;
                if (curRight == n)
                    return taps;
            } else {
                if (r.rightEdge <= curRight) continue;

                if (r.leftEdge <= curRight) {
                    if (r.rightEdge > potentialRightEdge) {
                        potentialRightEdge = r.rightEdge;
                    }
                    continue;
                }

                if (r.leftEdge == curRight+1) {
                    potentialRightEdge = Math.max(potentialRightEdge, r.rightEdge);
                    curRight = potentialRightEdge;
                    taps++;
                    //System.out.println("2 chose tap at: " + r.curIndex + " range: " + ranges[r.curIndex]);

                    if (curRight == n) return taps;
                    continue;
                }

                // r.leftEdge > curRight+1
                curRight = potentialRightEdge;
                taps++;
                //System.out.println("3 chose tap at: " + r.curIndex + " range: " + ranges[r.curIndex]);
                if (curRight == n) return taps;


                // now that curRight is updated, check for the current range r
                if (r.rightEdge <= curRight) continue;

                if (r.leftEdge <= curRight) {
                    if (r.rightEdge > potentialRightEdge) {
                        potentialRightEdge = r.rightEdge;
                    }
                    continue;
                }

                if (r.leftEdge > curRight + 1) return -1;

                if (r.leftEdge == curRight+1) {
                    potentialRightEdge = Math.max(potentialRightEdge, r.rightEdge);
                    curRight = potentialRightEdge;
                    taps++;
                    //System.out.println("4 chose tap at: " + r.curIndex + " range: " + ranges[r.curIndex]);

                    if (curRight == n) return taps;
                }
            }
        }

        if (potentialRightEdge >= n) {
            //System.out.println("Added a tap");
            return taps+1;
        }

        if (curRight < n) {
            //System.out.println("returning here");
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

        {
            int[] ranges = { 0, 3, 3, 2, 2, 4, 2, 1, 5, 1, 0, 1, 2, 3, 0, 3, 1, 1 };
            System.out.println(new MinNumberOfTapsWaterGarden().minTaps(17, ranges));
        }
*/
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
