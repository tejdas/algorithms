package net.lc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxPerformance {

    static class Pair {
        int index;
        int speed;

        public Pair(int index, int speed) {
            this.index = index;
            this.speed = speed;
        }
    }

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {

        int MOD = (int) (1e9 + 7);
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.speed));

        int[][] efficiencyArray = new int[efficiency.length][2];
        for (int i = 0; i < efficiency.length; i++) {
            int[] info = new int[] { i, efficiency[i] };
            efficiencyArray[i] = info;
        }

        Arrays.sort(efficiencyArray, (a, b) -> b[1] - a[1]);

        long cursum = 0;
        long teamperf = 0;

        for (int i = 0; i < k; i++) {
            int[] a = efficiencyArray[i];
            int index = a[0];
            int eff = a[1];
            cursum += speed[index];
            teamperf = Math.max(teamperf, cursum * eff);

            pq.add(new Pair(index, speed[index]));
        }

        for (int i = k; i < efficiencyArray.length; i++) {
            int[] a = efficiencyArray[i];
            int index = a[0];
            int perf = a[1];
            int spd = speed[index];

            long newsum = cursum - pq.peek().speed + spd;
            long newperf = newsum * perf;
            cursum = newsum;
            teamperf = Math.max(teamperf, newperf);
            pq.remove();
            pq.add(new Pair(index, spd));
        }

        return (int) teamperf % MOD;
    }

    public int maxPerformancew(int n, int[] speed, int[] efficiency, int k) {
        int MOD = (int) (1e9 + 7);
        int[][] engineers = new int[n][2];
        for (int i = 0; i < n; ++i)
            engineers[i] = new int[] { efficiency[i], speed[i] };

        Arrays.sort(engineers, (a, b) -> b[0] - a[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (a, b) -> a - b);
        long res = Long.MIN_VALUE, totalSpeed = 0;

        for (int[] engineer : engineers) {
            if (pq.size() == k)
                totalSpeed -= pq.poll();  // layoff the one with min speed
            pq.add(engineer[1]);
            totalSpeed = (totalSpeed + engineer[1]);
            res = Math.max(res, (totalSpeed * engineer[0]));  // min efficiency is the efficiency of new engineer
        }

        return (int) (res % MOD);
    }

    public static void main(String[] args) {
        /*
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, 10));
        pq.add(new Pair(1, 12));
        pq.add(new Pair(2, 7));

        while (!pq.isEmpty()) {
            System.out.println(pq.remove().speed);
        }

        {
            int[] speed = { 2, 10, 3, 1, 5, 8 };
            int[] efficiency = { 5, 4, 3, 9, 7, 2 };
            System.out.println(new MaxPerformance().maxPerformance(6, speed, efficiency, 2));
        }

        {

            int[] speed = { 2, 10, 3, 1, 5, 8 };
            int[] efficiency = { 5, 4, 3, 9, 7, 2 };
            System.out.println(new MaxPerformance().maxPerformance(6, speed, efficiency, 3));
        }

        {
            int[] speed = { 2, 10, 3, 1, 5, 8 };
            int[] efficiency = { 5, 4, 3, 9, 7, 2 };
            System.out.println(new MaxPerformance().maxPerformance(6, speed, efficiency, 4));
        }
*/
        {
            int[] speed = { 2, 8, 2 };
            int[] efficiency = { 2, 7, 1 };
            System.out.println(new MaxPerformance().maxPerformance(3, speed, efficiency, 2));
        }
    }
}
