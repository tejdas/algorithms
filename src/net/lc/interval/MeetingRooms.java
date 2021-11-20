package net.lc.interval;

import java.util.Arrays;

/**
 * 252
 */
public class MeetingRooms {
    static class Interval implements Comparable<Interval> {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.end == o.end) return Integer.compare(this.start, o.start);
            return Integer.compare(this.end, o.end);
        }
    }

    public boolean canAttendMeetings(int[][] input) {

        if (input == null || input.length == 0) {
            return true;
        }

        Interval[] intervals = new Interval[input.length];
        int index = 0;
        for (int[] m : input) {
            intervals[index++] = new Interval(m[0], m[1]);
        }

        Arrays.sort(intervals);

        Interval prev = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            Interval cur = intervals[i];
            if (cur.start < prev.end) return false;

            prev = cur;
        }

        return true;
    }
}
