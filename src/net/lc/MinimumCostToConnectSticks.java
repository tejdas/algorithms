package net.lc;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/minimum-cost-to-connect-sticks/submissions/
 * PriorityQueue
 * Greedy
 */
public class MinimumCostToConnectSticks {
    public int connectSticks(int[] sticks) {
        if (sticks == null || sticks.length == 0) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int stick : sticks) {
            pq.add(stick);
        }

        int sum = 0;

        while (!pq.isEmpty()) {
            int stick1 = pq.remove();
            if (pq.isEmpty()) break;
            int stick2 = pq.remove();

            int newstick = stick1 + stick2;
            sum += newstick;

            if (pq.isEmpty()) break;
            pq.add(newstick);
        }
        return sum;
    }
}
