package net.lc.interval;

import java.util.*;

public class AddBoldTagInString {
    static class Interval implements Comparable<Interval> {
        int begin;
        int end;

        public Interval(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.end == o.end) {
                return Integer.compare(this.begin, o.begin);
            }
            return Integer.compare(this.end, o.end);
        }
    }

    static class IntervalBoundary implements Comparable<IntervalBoundary> {
        int index;
        boolean isBegin;

        public IntervalBoundary(int index, boolean isBegin) {
            this.index = index;
            this.isBegin = isBegin;
        }

        @Override
        public int compareTo(IntervalBoundary o) {
            if (index != o.index) {
                return Integer.compare(index, o.index);
            }

            if (isBegin) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public String addBoldTag(String s, String[] dict) {
        if (dict == null || dict.length == 0) return s;

        List<IntervalBoundary> intervals = new ArrayList<>();

        for (String word : dict) {
            int startIndex = 0;
            while (true) {
                int index = s.indexOf(word, startIndex);
                if (index == -1) break;
                if (index != -1) {
                    intervals.add(new IntervalBoundary(index, true));
                    intervals.add(new IntervalBoundary(index + word.length() - 1, false));
                    startIndex = index + 1;
                }
            }
        }

        IntervalBoundary[] intervalArray = intervals.toArray(new IntervalBoundary[intervals.size()]);
        Arrays.sort(intervalArray);

        List<Interval> result = new ArrayList<>();

        int begin = -1;

        int depth = 0;
        for (IntervalBoundary interval : intervalArray) {
            if (interval.isBegin) {
                if (depth == 0) {
                    begin = interval.index;
                }
                depth++;
            } else {
                depth--;
                if (depth == 0) {
                    result.add(new Interval(begin, interval.index));
                }
            }
        }

        PriorityQueue<Interval> pq = new PriorityQueue<>();
        for (Interval i : result) {
            pq.add(i);
        }

        result.clear();

        while (!pq.isEmpty()) {
            Interval interval = pq.remove();
            if (result.isEmpty()) {
                result.add(interval);
            } else {
                int lastIndex = result.size()-1;
                if (interval.begin == result.get(lastIndex).end+1) {
                    result.set(lastIndex, new Interval(result.get(lastIndex).begin, interval.end));
                } else {
                    result.add(interval);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        int lastIndex = 0;

        for (Interval interval : result) {
            if (lastIndex < interval.begin) {
                sb.append(s.substring(lastIndex, interval.begin));
            }
            sb.append("<b>");
            String sub = s.substring(interval.begin, interval.end+1);
            sb.append(sub);
            sb.append("</b>");
            lastIndex = interval.end + 1;
        }

        if (lastIndex < s.length()) {
            sb.append(s.substring(lastIndex));
        }

        return sb.toString();
    }
}
