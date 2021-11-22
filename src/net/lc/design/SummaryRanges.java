package net.lc.design;

import java.util.*;

public class SummaryRanges {
    static class Interval implements Comparable<Interval> {
        int start;
        int end;

        Interval(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.start == o.start && this.end==o.end) return 0;

            if (this.end == o.start)
                return Integer.compare(this.start, o.start);

            return Integer.compare(this.end, o.start);
        }

        @Override
        public String toString() {
            return "Interval{" + "start=" + start + ", end=" + end + '}';
        }

        public boolean isAdjNext(Interval val) {
            return ((val.start - this.end) == 1);
        }
    }

    private final TreeSet<Interval> treeSet = new TreeSet<>();

    public SummaryRanges() {
    }

    public void addNum(int val) {
        Interval toAdd = new Interval(val, val);

        if (treeSet.isEmpty()) {
            treeSet.add(toAdd);
            return;
        }

        Interval lower = treeSet.lower(toAdd);

        if (lower != null) {
            if (val >= lower.start && val <= lower.end) {
                return;
            }
        }

        Interval higher = treeSet.higher(toAdd);

        if (lower != null && higher != null) {

            boolean flagLeft = lower.isAdjNext(toAdd);
            boolean flagRight = toAdd.isAdjNext(higher);

            if (flagLeft && flagRight) {
                treeSet.remove(lower);
                treeSet.remove(higher);
                treeSet.add(new Interval(lower.start, higher.end));
            } else if (flagLeft) {
                treeSet.remove(lower);
                treeSet.add(new Interval(lower.start, val));
            } else if (flagRight) {
                treeSet.remove(higher);
                treeSet.add(new Interval(val, higher.end));
            } else {
                treeSet.add(toAdd);
            }
            return;
        } else if (lower != null) {
            boolean flagLeft = lower.isAdjNext(toAdd);
            if (flagLeft) {
                treeSet.remove(lower);
                treeSet.add(new Interval(lower.start, val));
            } else {
                treeSet.add(toAdd);
            }
        } else if (higher != null) {
            boolean flagRight = toAdd.isAdjNext(higher);
            if (flagRight) {
                treeSet.remove(higher);
                treeSet.add(new Interval(val, higher.end));
            } else {
                treeSet.add(toAdd);
            }
        }
    }

    public List<Interval> getIntervals() {
        List<Interval> result = new ArrayList<>();

        Iterator<Interval> it = treeSet.iterator();
        while (it.hasNext()) {
            Interval cur = it.next();
            result.add(cur);
        }
        return result;
    }
}
