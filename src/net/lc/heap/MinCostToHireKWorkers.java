package net.lc.heap;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 857
 * Heap
 */
public class MinCostToHireKWorkers {
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        if (quality == null || wage == null)
            return 0;
        if (quality.length < K)
            return 0;

        double result = Double.MAX_VALUE;

        for (int i = 0; i < quality.length; i++) {
            double minCost = minCostWithWorker(i, quality, wage, K);
            if (minCost == Double.MAX_VALUE)
                continue;
            result = Math.min(result, minCost);
        }
        return result;
    }

    private double minCostWithWorker(int workerIndex, int[] quality, int[] wage, int k) {
        double totalCost = wage[workerIndex];
        PriorityQueue<Double> pq = new PriorityQueue<>(Collections.reverseOrder());

        double ratio = (double) wage[workerIndex] / quality[workerIndex];

        for (int i = 0; i < quality.length; i++) {
            if (i == workerIndex)
                continue;
            double payment = quality[i] * ratio;
            if (payment < wage[i])
                continue;

            pq.add(payment);
            if (pq.size() > k - 1)
                pq.remove();
        }

        if (pq.size() < k - 1)
            return Double.MAX_VALUE;
        double sum = 0;
        for (double d : pq)
            sum += d;
        return sum + wage[workerIndex];
    }
}
