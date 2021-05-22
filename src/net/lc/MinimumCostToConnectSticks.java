package net.lc;

import java.util.PriorityQueue;

/**
 * 1167
 * https://leetcode.com/problems/minimum-cost-to-connect-sticks/submissions/
 * PriorityQueue
 * Greedy
 *
 * Always pick up the two smallest sticks, and combine those, and put it back to PQ.
 * By always picking up the smallest two sticks greedily, we make sure to minimize
 * the cost.
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
