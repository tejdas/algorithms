package net.lc;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/car-pooling/submissions/
 * Greedy
 * Priority-queue
 */
public class CarPooling {
    static class Loc implements Comparable<Loc> {
        int distance;
        int passngers;
        int start;

        public Loc(int distance, int passngers, int start) {
            this.distance = distance;
            this.passngers = passngers;
            this.start = start;
        }

        @Override
        public int compareTo(Loc o) {
            if (this.distance == o.distance) {
                return Integer.compare(o.start, this.start);
            }
            return Integer.compare(this.distance, o.distance);
        }
    }

    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<Loc> pq = new PriorityQueue<>();
        for (int[] trip : trips) {
            pq.add(new Loc(trip[1], trip[0], 0));
            pq.add(new Loc(trip[2], trip[0], 1));
        }

        int carCapacity = 0;
        while (!pq.isEmpty()) {
            Loc loc = pq.remove();
            if (loc.start == 1) {
                // end
                carCapacity -= loc.passngers;
            } else {
                carCapacity += loc.passngers;
                if (carCapacity > capacity) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        {
            int[][] trips = { { 2, 1, 5 }, { 3, 3, 7 } };
            System.out.println(new CarPooling().carPooling(trips, 4));
        }
        {
            int[][] trips = { { 2, 1, 5 }, { 3, 3, 7 } };
            System.out.println(new CarPooling().carPooling(trips, 5));
        }
        {
            int[][] trips = { { 2, 1, 5 }, { 3, 5, 7 } };
            System.out.println(new CarPooling().carPooling(trips, 3));
        }
        {
            int[][] trips = { {3,2,7},{3,7,9},{8,3,9} };
            System.out.println(new CarPooling().carPooling(trips, 11));
        }
    }
}
