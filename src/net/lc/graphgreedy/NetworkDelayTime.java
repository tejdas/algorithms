package net.lc.graphgreedy;

import java.util.*;

/**
 * 743
 * Dijkstra
 * Greedy
 */
public class NetworkDelayTime {
    private static final class DEdge {
        int weight;
        int otherNode;

        public DEdge(int weight, int otherNode) {
            this.weight = weight;
            this.otherNode = otherNode;
        }
    }

    static class DistanceToVertex implements Comparable<DistanceToVertex> {
        int id;
        int distance;

        public DistanceToVertex(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(DistanceToVertex o) {
            return Integer.compare(this.distance, o.distance);
        }
    }
    private final Map<Integer, List<DEdge>> adjMap = new HashMap<>();

    public int networkDelayTime(int[][] times, int N, int K) {

        adjMap.clear();
        for (int i = 1; i <= N; i++) {
            adjMap.put(i, new ArrayList<>());
        }

        for (int i = 0; i < times.length; i++) {
            int[] array = times[i];
            List<DEdge> list = adjMap.get(array[0]);
            if (list == null) {
                list = new ArrayList<>();
                adjMap.put(array[0], list);
            }

            list.add(new DEdge(array[2], array[1]));
        }

        Map<Integer, Integer> distanceMap = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            if (i == K) distanceMap.put(i, 0);
            else distanceMap.put(i, Integer.MAX_VALUE);
        }

        PriorityQueue<DistanceToVertex> pq = new PriorityQueue<>();
        pq.add(new DistanceToVertex(K, 0));

        int maxDistance = 0;

        while (!pq.isEmpty()) {
            DistanceToVertex dv = pq.remove();
            int distCurNode = distanceMap.get(dv.id);

            List<DEdge> neighbors = adjMap.get(dv.id);

            for (DEdge neighbor : neighbors) {
                int neighborDist = distanceMap.get(neighbor.otherNode);

                if (neighborDist > distCurNode+neighbor.weight) {
                    neighborDist = distCurNode+neighbor.weight;
                    distanceMap.put(neighbor.otherNode, neighborDist);

                    pq.add(new DistanceToVertex(neighbor.otherNode, neighborDist));
                }
            }
        }

        for (int val : distanceMap.values()) {
            if (val == Integer.MAX_VALUE) return -1;
            maxDistance = Math.max(maxDistance, val);
        }
        return maxDistance;
    }
}
