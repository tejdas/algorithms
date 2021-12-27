package net.lc.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/meeting-rooms-ii/
 * 253
 * Greedy
 */
class MeetingRoomsII {
    static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public Interval() {
        }
    }

    private static class CKey implements Comparable<CKey> {

        public CKey(int time, boolean isBegin) {
            super();
            this.time = time;
            this.isBegin = isBegin;
        }

        int time;
        boolean isBegin;

        @Override
        public int compareTo(CKey o) {

            if (time != o.time) {
                return Integer.compare(time, o.time);
            }

            if (isBegin != o.isBegin) {
                if (!isBegin) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return 0;
        }
    }

    public int minMeetingRooms(Interval[] intervals) {

        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        List<CKey> ckeys = new ArrayList<>();

        for (Interval interval : intervals) {
            ckeys.add(new CKey(interval.start, true));
            ckeys.add(new CKey(interval.end, false));
        }

        CKey[] array = ckeys.toArray(new CKey[ckeys.size()]);

        Arrays.sort(array);

        int minConfRoomsNeeded = 0;

        int concurrentMeetingCount = 0;

        for (CKey key : array) {
            if (key.isBegin) {
                concurrentMeetingCount++;
                minConfRoomsNeeded = Math.max(minConfRoomsNeeded, concurrentMeetingCount);
            } else {
                concurrentMeetingCount--;
            }
        }
        return minConfRoomsNeeded;
    }
}
