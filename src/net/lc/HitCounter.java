package net.lc;

import java.util.Arrays;

/**
 * Design
 * 362
 */
public class HitCounter {
    private final int[] hitstore = new int[300];
    private final int[] lastHitMin = new int[300];
    /** Initialize your data structure here. */
    public HitCounter() {
        Arrays.fill(hitstore, 0);
        Arrays.fill(lastHitMin, 0);
    }

    /**
     * Record a hit.
     * lastHitMin stores the last minute in which the hitcount is being stored.
     * For example, on index 10, it will store hits for mins:
     * 10
     * 310
     * 610 etc.
     * If the last minute matches with timestamp, keep adding to count. If not, it means
     * we have gone past 5 mins. Therefore, store the new timestamp, and reset count to 1.
     * @param timestamp
     */
    public void hit(int timestamp) {
        int timestampMod = timestamp % 300;
        int lastHitIndex = lastHitMin[timestampMod];

        if (lastHitIndex != timestamp) {
            hitstore[timestampMod] = 1;
            lastHitMin[timestampMod] = timestamp;
        } else {
            hitstore[timestampMod]++;
        }
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int sum = 0;

        for (int t = timestamp; t > timestamp-300 && t >= 0; t--) {
            int mod = t % 300;
            if (lastHitMin[mod] == t) {
                sum += hitstore[mod];
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
