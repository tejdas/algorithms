package net.lc;

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

        @Override
        public String toString() {
            String str = isBegin ? "begin" : "end";
            return "CKey [id=" + id + ", time=" + time + ", " + str + "]";
        }

        public CKey(String id, int time, boolean isBegin) {
            super();
            this.id = id;
            this.time = time;
            this.isBegin = isBegin;
        }

        String id;
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
            // TODO Auto-generated method stub
            return id.compareTo(o.id);
        }
    }

    public int minMeetingRooms(Interval[] intervals) {

        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        List<CKey> ckeys = new ArrayList<>();

        int idx = 0;
        for (Interval interval : intervals) {
            String id = String.valueOf(idx);
            ckeys.add(new CKey(id, interval.start, true));
            ckeys.add(new CKey(id, interval.end, false));
            idx++;
        }

        CKey[] array = ckeys.toArray(new CKey[ckeys.size()]);

        Arrays.sort(array);

        int minConfRoomsNeeded = 0;

        int concurrentMeetingCount = 0;

        for (CKey key : array) {
            if (key.isBegin) {
                concurrentMeetingCount++;
                if (concurrentMeetingCount > minConfRoomsNeeded) {
                    minConfRoomsNeeded = concurrentMeetingCount;
                }
            } else {
                concurrentMeetingCount--;
            }
        }
        return minConfRoomsNeeded;
    }
}
