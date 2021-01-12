package net.lc;

import java.util.*;

/**
 * 815
 * https://leetcode.com/problems/bus-routes/
 * BFS
 */
public class BusRoutes {
    public int numBusesToDestination(int[][] routes, int S, int T) {
        routeToBusMap.clear();
        adjMap.clear();

        buildGraph(routes);
        return bfs(S, T);
    }

    private final Map<Integer, Set<Integer>> routeToBusMap = new HashMap<>();

    private final Map<Integer, Set<Integer>> adjMap = new HashMap<>();

    private void buildGraph(int[][] routes) {
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                Set<Integer> list = routeToBusMap.computeIfAbsent(stop, k -> new HashSet<>());
                list.add(i);
            }
        }

        for (Set<Integer> l : routeToBusMap.values()) {
            if (l.size() > 0) {
                for (int val : l) {
                    Set<Integer> s = new HashSet<>(l);
                    s.remove(val);
                    Set<Integer> set = adjMap.computeIfAbsent(val, k -> new HashSet<>());
                    set.addAll(s);
                }
            }
        }
    }

    private int bfs(int s, int t) {
        if (s == t) return 0;
        Set<Integer> sourceSet = routeToBusMap.get(s);
        Set<Integer> destSet = routeToBusMap.get(t);

        if (sourceSet.size() == 1 && destSet.size() == 1) {
            if (sourceSet.containsAll(destSet)) {
                return 1;
            }
        }

        for (int val : sourceSet) {
            if (destSet.contains(val)) {
                return 1;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distanceMap = new HashMap<>();

        for (int startBus : sourceSet) {
            if (distanceMap.containsKey(startBus)) continue;

            distanceMap.put(startBus, 0);
            queue.add(startBus);

            while (!queue.isEmpty()) {
                int curNode = queue.remove();
                //System.out.println(curNode);

                int distance = distanceMap.get(curNode);

                Set<Integer> adjNodes = adjMap.get(curNode);
                for (int adj : adjNodes) {
                    if (!distanceMap.containsKey(adj)) {
                        queue.add(adj);
                        distanceMap.put(adj, distance + 1);
                    }
                }
            }
        }

        int minHops = Integer.MAX_VALUE;
        for (int dest : destSet) {
            if (distanceMap.containsKey(dest)) {
                minHops = Math.min(minHops, distanceMap.get(dest));
            }
        }

        return (minHops == Integer.MAX_VALUE)? -1 : minHops+1;
    }
}
