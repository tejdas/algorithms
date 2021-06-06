package net.lc.heap;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/k-closest-points-to-origin/submissions/
 * Priority-Queue
 * 973
 */
public class KClosestPoints {

    static class Point implements Comparable<Point> {
        int x;
        int y;
        double distance;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.distance = Math.sqrt(x*x + y*y);
        }

        @Override
        public int compareTo(Point o) {
            return Double.compare(o.distance, this.distance);
        }
    }
    public int[][] kClosest(int[][] points, int K) {

        int[][] res = new int[K][2];

        PriorityQueue<Point> pq = new PriorityQueue<>();

        for (int[] point : points) {
            Point p = new Point(point[0], point[1]);
            if (pq.size() < K) {
                pq.add(p);
            } else {
                if (p.distance < pq.peek().distance) {
                    pq.remove();
                    pq.add(p);
                }
            }
        }

        int index = 0;
        while (!pq.isEmpty()) {
            Point p = pq.remove();
            res[index][0] = p.x;
            res[index][1] = p.y;
            index++;
        }
        return res;
    }


}
