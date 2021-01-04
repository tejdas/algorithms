package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MinConfRoomCalculator {
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

    private static int calculate(List<CKey> ckeys) {
        CKey[] array = ckeys.toArray(new CKey[ckeys.size()]);

        Arrays.sort(array);

        int minConfRoomsNeeded = 0;

        int concurrentMeetingCount = 0;

        for (CKey key : array) {
            System.out.println(key.toString());
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

    static class MeetingInfo {
        @Override
        public String toString() {
            return "MeetingInfo [id=" + id + ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
        }
        public MeetingInfo(String id, int beginTime, int endTime) {
            this.id = id;
            this.beginTime = beginTime;
            this.endTime = endTime;
        }

        String id;
        int beginTime;
        int endTime;
    }

    public static void main(String[] args) {

        final Random r = new Random();
        int numMeetings = 10;

        int index = 0;

        List<MeetingInfo> meetings = new ArrayList<>();

        while (index < numMeetings) {
            int beginTime = r.nextInt(1000);
            int endTime = r.nextInt(1200);

            if (endTime <= beginTime) {
                continue;
            }

            meetings.add(new MeetingInfo(String.valueOf(index), beginTime, endTime));
            index++;
        }

        List<CKey> meetingKeys = new ArrayList<>();

        for (MeetingInfo m : meetings) {
            System.out.println(m.toString());

            meetingKeys.add(new CKey(m.id, m.beginTime, true));
            meetingKeys.add(new CKey(m.id, m.endTime, false));
        }

        int rooms = calculate(meetingKeys);

        System.out.println("min rooms needed: " + rooms);
    }
}
