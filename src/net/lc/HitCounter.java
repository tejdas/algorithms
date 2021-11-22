package net.lc;

/**
 * Design
 * 362
 */
public class HitCounter {
    static class HitCountInfo {
        int count;
        int second;
    }

    private final HitCountInfo[] hitstore = new HitCountInfo[300];

    public HitCounter() {
    }

    /**
     * Record a hit.
     * lastHitSec stores the last second in which the hitcount is being stored.
     * For example, on index 10, it will store hits for mins:
     * 10
     * 310
     * 610 etc.
     * If the last second matches with timestamp, keep adding to count. If not, it means
     * we have gone past 5 mins. Therefore, store the new timestamp, and reset count to 1.
     * @param timestamp
     */
    public void hit(int timestamp) {
        int timestampMod = timestamp % 300;
        HitCountInfo info = hitstore[timestampMod];
        if (info == null) {
            hitstore[timestampMod] = new HitCountInfo();
            hitstore[timestampMod].count = 1;
            hitstore[timestampMod].second = timestamp;
        } else {
            if (info.second == timestamp) {
                info.count++;
            } else {
                info.second = timestamp;
                info.count = 1;
            }
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int sum = 0;

        for (int t = timestamp; t > timestamp-300 && t >= 0; t--) {
            int mod = t % 300;
            HitCountInfo info = hitstore[mod];
            if (info != null && info.second == t) {
                sum += info.count;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        HitCounter hc = new HitCounter();
        hc.hit(1);
        hc.hit(2);
        hc.hit(3);
        System.out.println(hc.getHits(4));

        hc.hit(300);
        System.out.println(hc.getHits(300));
        System.out.println(hc.getHits(301));
    }
}
