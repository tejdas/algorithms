package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class PrimMST {
    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int M = Integer.parseInt(temp[1]);

        if (N < 1 || N > 3000 || M < 1 || M > 4500000) {
            return;
        }

        for (int i = 0; i < M; i++) {
            temp = bfr.readLine().split(" ");
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);
            long w = Long.parseLong(temp[2]);

            Pair pa = new Pair(v1, v2);
            Pair pb = new Pair(v2, v1);

            if (edgeMap.containsKey(pa)) {
                long wt = edgeMap.get(pa);
                if (w < wt) {
                    edgeMap.put(pa, w);
                }
            } else if (edgeMap.containsKey(pb)) {
                long wt = edgeMap.get(pb);
                if (w < wt) {
                    edgeMap.put(pb, w);
                }
            } else {
                edgeMap.put(pa, w);
            }
        }

        int startNode = Integer.parseInt(bfr.readLine());

        buildAdjacencyMap(N);
        long weight = buildMinimumSpanningTree(startNode, N);
        System.out.println(weight);

        bfr.close();
    }

    static class Pair {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + v1;
            result = prime * result + v2;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (v1 != other.v1)
                return false;
            if (v2 != other.v2)
                return false;
            return true;
        }

        public Pair(int v1, int v2) {
            super();
            this.v1 = v1;
            this.v2 = v2;
        }

        int v1;
        int v2;

    }

    static Map<Pair, Long> edgeMap = new HashMap<>();

    static long getWeight(int v1, int v2) {
        Pair pa = new Pair(v1, v2);
        Pair pb = new Pair(v2, v1);

        if (edgeMap.containsKey(pa)) {
            return edgeMap.get(pa);
        } else {
            return edgeMap.get(pb);
        }
    }

    static class Edge implements Comparable<Edge> {

        public Edge(int v1, int v2, long w) {
            super();
            this.v1 = v1;
            this.v2 = v2;
            this.w = w;
        }

        public int other(int v) {
            if (v == v1)
                return v2;
            else
                return v1;
        }

        int v1;
        int v2;
        long w;

        @Override
        public int compareTo(Edge o) {
            if (this.w == o.w)
                return Integer.compare(this.v1 + this.v2, o.v1 + o.v2);
            else
                return Long.compare(this.w, o.w);
        }
    }

    static final Map<Integer, List<Integer>> adjacencyMap = new HashMap<>();

    static void buildAdjacencyMap(int N) {
        for (int i = 1; i <= N; i++) {
            adjacencyMap.put(i, new ArrayList<Integer>());
        }

        for (Pair edge : edgeMap.keySet()) {
            adjacencyMap.get(edge.v1).add(edge.v2);
            adjacencyMap.get(edge.v2).add(edge.v1);
        }

    }

    static long buildMinimumSpanningTree(int startNode, int N) {
        int source = startNode;
        Set<Integer> visited = new HashSet<>();

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (int adjNode : adjacencyMap.get(source)) {
            long weight = getWeight(source, adjNode);
            pq.offer(new Edge(source, adjNode, weight));
        }
        visited.add(source);

        long totalWeight = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.remove();
            if (visited.contains(edge.v1) && visited.contains(edge.v2)) {
                continue;
            }

            int curNode = !visited.contains(edge.v1) ? edge.v1 : edge.v2;
            visited.add(curNode);

            totalWeight += edge.w;
            // System.out.println("adding: " + edge.v1 + ":" + edge.v2);

            for (int adjNode : adjacencyMap.get(curNode)) {
                if (!visited.contains(adjNode)) {
                    long weight = getWeight(curNode, adjNode);
                    pq.offer(new Edge(curNode, adjNode, weight));
                }
            }
        }

        return totalWeight;
    }
}
