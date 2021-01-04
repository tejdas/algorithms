package xxx.yyy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MergeIntervalDriver {

    static class Interval implements Comparable<Interval> {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + high;
            result = prime * result + low;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Interval other = (Interval) obj;
            if (high != other.high)
                return false;
            if (low != other.low)
                return false;
            return true;
        }
        public Interval(final int low, final int high) {
            this.low = low;
            this.high = high;
        }

        @Override
        public int compareTo(final Interval o) {
            return Integer.compare(this.low,  o.low);
        }

        public int low;
        public int high;
    }

    public static void main1(final String[] args) {
        final PriorityQueue<Interval> intervals = new PriorityQueue<>();
        intervals.add(new Interval(1, 5));
        intervals.add(new Interval(3, 10));
        intervals.add(new Interval(7, 30));
        intervals.add(new Interval(10, 25));
        intervals.add(new Interval(32, 35));
        intervals.add(new Interval(34, 40));
        final int val = range(intervals);
        System.out.println(val);
    }

    public static void main2(final String[] args) {
        final PriorityQueue<Interval> intervals = new PriorityQueue<>();
        intervals.add(new Interval(2, 7));
        intervals.add(new Interval(5, 8));
        intervals.add(new Interval(10, 15));

        final int val = kthSmallest(intervals, 10);
        System.out.println(val);
    }

    public static void main(final String[] args) {
        final List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(2, 7));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(12, 15));
        intervals.add(new Interval(17, 22));
        intervals.add(new Interval(25, 30));

        encompassingRange(13, 14, intervals);

    }

    static void encompassingRange(final int low, final int high, final List<Interval> intervals) {
        if (low > high) {
            throw new IllegalArgumentException();
        }

        if (intervals.isEmpty()) {
            return;
        }

        Collections.sort(intervals);

        final List<Interval> result = new ArrayList<>();
        boolean foundUpperLimit = false;
        for (final Interval i : intervals) {
            if (i.high < low) {
                continue;
            }

            if (i.low > high) {
                foundUpperLimit = true;
                break;
            }

            result.add(i);
        }

        if (!foundUpperLimit) {
            System.out.println("Did not find upper limit");
        }

        for (final Interval i : result) {
            System.out.println(i.low + "  " + i.high);
        }
    }

    static int range(final PriorityQueue<Interval> intervals) {
        int range = 0;
        int currentMax = 0;
        for (final Interval iter : intervals) {
            if (iter.high < currentMax) {
                continue;
            } else if (iter.low > currentMax) {
                range += (iter.high-iter.low);
                currentMax = iter.high;
            } else {
                range +=  (iter.high-currentMax);
                currentMax = iter.high;
            }
        }
        return range;
    }

    static int kthSmallest(final PriorityQueue<Interval> intervals, final int k) {
        int numbersSeen = 0;
        int currentMax = Integer.MIN_VALUE;
        for (final Interval iter : intervals) {
            if (iter.high < currentMax) {
                continue;
            } else if (iter.low > currentMax) {

                if (numbersSeen + (iter.high-iter.low+1) > k) {
                    return iter.low + (k-(numbersSeen+1));
                } else {
                    numbersSeen += (iter.high-iter.low) + 1;
                    currentMax = iter.high;
                }
            } else {
                if (numbersSeen + (iter.high-currentMax) > k) {
                    return currentMax + (k-numbersSeen);
                } else {
                    numbersSeen += (iter.high-currentMax);
                    currentMax = iter.high;
                }
            }
        }
        return -1;
    }
}
