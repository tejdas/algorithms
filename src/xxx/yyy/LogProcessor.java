package xxx.yyy;

import java.util.PriorityQueue;

public class LogProcessor {

    static final PriorityQueue<LogInfo> pq = new PriorityQueue<>();

    private static class LogInfo implements Comparable<LogInfo> {

        public LogInfo(final long time, final boolean isStart, final int memUsage) {
            super();
            this.time = time;
            this.isStart = isStart;
            this.memUsage = memUsage;
        }
        long time;
        boolean isStart;
        int memUsage;

        @Override
        public int compareTo(final LogInfo o) {
            return Long.compare(this.time,  o.time);
        }
    }

    static void add(final long startTime, final long endTime, final int memUsage) {
        pq.add(new LogInfo(startTime, true, memUsage));
        pq.add(new LogInfo(endTime, false, memUsage));
    }

    static void findHighestMemUsage() {
        int highestMemUsage = 0;
        long startTimeOfHighestMemUsage = -1;

        int currentMemUsage = 0;

        while (!pq.isEmpty()) {
            final LogInfo log = pq.poll();

            if (log.isStart) {
                currentMemUsage += log.memUsage;
                if (currentMemUsage > highestMemUsage) {
                    highestMemUsage = currentMemUsage;
                    startTimeOfHighestMemUsage = log.time;
                }
            } else {
                currentMemUsage -= log.memUsage;
            }
        }

        System.out.println("startTimeOfHighestMemUsage: " + startTimeOfHighestMemUsage);
        System.out.println("highestMemUsage: " + highestMemUsage);
    }

    public static void main1(final String[] args) {
        add(100207, 100208, 2);
        add(100305, 100307, 5);
        add(100207, 100209, 4);
        add(111515, 121212, 1);

        findHighestMemUsage();
    }

    public static void main(final String[] args) {

        // s1 e1 s2 s3 e3 s4 s5 e2 e5 e4
        // 35 40 45 50 55 60 65 70 75 80

        add(35, 40, 5);
        add(45, 70, 10);
        add(50, 55, 12);
        add(60, 80, 7);
        add(65, 75, 8);

        findHighestMemUsage();
    }
}
