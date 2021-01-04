package net.lc;

import java.util.*;

public class EvaluateDivision {
    static class DEdge {
        String from;
        String to;

        public DEdge(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            DEdge dEdge = (DEdge) o;

            if (from != null ? !from.equals(dEdge.from) : dEdge.from != null)
                return false;
            return to != null ? to.equals(dEdge.to) : dEdge.to == null;
        }

        @Override
        public int hashCode() {
            int result = from != null ? from.hashCode() : 0;
            result = 31 * result + (to != null ? to.hashCode() : 0);
            return result;
        }
    }

    private final Set<String> vertexSet = new HashSet<>();
    private final Map<String, Set<String>> map = new HashMap<>();
    private final Map<DEdge, Double> edgeMap = new HashMap<>();
    private final Set<String> visited = new HashSet<>();
    private final Set<String> notVisited = new HashSet<>();
    private final List<Set<String>> islands = new ArrayList<>();

    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        buildMap(equations, values);

        double[] ret = new double[queries.length];
        for (int i = 0; i < ret.length; i++) {
            String[] query = queries[i];
            ret[i] = evalQuery(query);
        }
        return ret;
    }

    private void buildMap(String[][] equations, double[] values) {
        vertexSet.clear();
        map.clear();
        edgeMap.clear();
        for (int i = 0; i < values.length; i++) {
            String[] pair = equations[i];
            double val = values[i];
            vertexSet.add(pair[0]);
            vertexSet.add(pair[1]);

            edgeMap.put(new DEdge(pair[0], pair[1]), val);
            edgeMap.put(new DEdge(pair[1], pair[0]), 1/val);

            Set<String> vertices1 = map.computeIfAbsent(pair[0], k -> new HashSet<>());
            vertices1.add(pair[1]);

            Set<String> vertices2 = map.computeIfAbsent(pair[1], k -> new HashSet<>());
            vertices2.add(pair[0]);
        }
        buildIslands();
    }

    private void buildIslands() {
        notVisited.clear();
        visited.clear();
        islands.clear();
        notVisited.addAll(vertexSet);

        while (!notVisited.isEmpty()) {
            Set<String> island = new HashSet<>();
            String curNode = notVisited.iterator().next();
            dfs(curNode, island);
            islands.add(island);
        }

        for (Set<String> island : islands) {
            //System.out.println(Arrays.toString(island.toArray()));
        }
    }

    private void dfs(String cur, Set<String> island) {
        if (visited.contains(cur)) {
            return;
        }

        visited.add(cur);
        island.add(cur);
        notVisited.remove(cur);

        for (String adj : map.get(cur)) {
            if (!visited.contains(adj)) {
                dfs(adj, island);
            }
        }
    }

    private double evalQuery(String[] query) {
        String from = query[0];
        String to = query[1];

        if (!vertexSet.contains(from) || !vertexSet.contains(to)) {
            return -1.0;
        }

        if (from.equals(to)) {
            return 1.0;
        }

        boolean islandFound = false;
        for (Set<String> island : islands) {
            if (island.contains(from) && island.contains(to)) {
                islandFound = true;
                break;
            } else if (island.contains(from) && !island.contains(to)) {
                return -1.0;
            } else if (!island.contains(from) && island.contains(to)) {
                return -1.0;
            }
        }

        if (!islandFound) {
            return -1.0;
        }

        return compute(from, to);
    }

    private double compute(String from, String to) {
        DEdge forward = new DEdge(from, to);
        DEdge backward = new DEdge(to, from);
        if (edgeMap.containsKey(forward)) {
            return edgeMap.get(forward);
        } else if (edgeMap.containsKey(backward)) {
            return edgeMap.get(backward);
        }

        Stack<DEdge> path = new Stack<>();
        Set<String> visited = new HashSet<>();

        findDFSPath(from, to, path, visited);
        return compute(path);
    }

    private boolean findDFSPath(String cur, String target, Stack<DEdge> path, Set<String> visited) {
        if (cur.equals(target)) return true;
        if (visited.contains(cur)) return false;
        visited.add(cur);
        if (map.containsKey(cur)) {
            for (String adj : map.get(cur)) {
                if (!visited.contains(adj)) {
                    path.push(new DEdge(cur, adj));
                    if (findDFSPath(adj, target, path, visited)) {
                        return true;
                    }
                    path.pop();
                }
            }
        }
        return false;
    }

    private double compute(Stack<DEdge> path) {
        double val = 1;
        for (DEdge dEdge : path) {
            val *= edgeMap.get(dEdge);
        }
        return val;
    }
}
