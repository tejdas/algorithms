package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class KruskalMST {
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

        buildMinimumSpanningTree(N);

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

    static Map<Integer, Integer> rootMap = new HashMap<>();

    static class Edge implements Comparable<Edge> {

        public Edge(int v1, int v2, long w) {
            super();
            this.v1 = v1;
            this.v2 = v2;
            this.w = w;
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

    static int getRoot(int v) {
        int temp = v;
        while (true) {
            int root = rootMap.get(temp);
            if (root == temp) {
                return root;
            } else {
                temp = root;
            }
        }
    }

    /**
     * Minimum Spanning Tree
     * PriorityQueue
     * Greedy
     * Pick up the smallest edge.
     * @param N
     */
    static void buildMinimumSpanningTree(int N) {

        final PriorityQueue<Edge> pq = new PriorityQueue<>();

        for (Entry<Pair, Long> entry : edgeMap.entrySet()) {
            pq.add(new Edge(entry.getKey().v1, entry.getKey().v2, entry.getValue()));
        }

        long totalWeight = 0;
        Set<Edge> mstEdges = new HashSet<>();
        while ((mstEdges.size() < N - 1) && !pq.isEmpty()) {
            Edge edge = pq.remove();

            if (!rootMap.containsKey(edge.v1) && !rootMap.containsKey(edge.v2)) {
                rootMap.put(edge.v1, edge.v1);
                rootMap.put(edge.v2, edge.v1);
            } else if (rootMap.containsKey(edge.v1) && !rootMap.containsKey(edge.v2)) {
                int v = getRoot(edge.v1);
                rootMap.put(edge.v2, v);
            } else if (rootMap.containsKey(edge.v2) && !rootMap.containsKey(edge.v1)) {
                int v = getRoot(edge.v2);
                rootMap.put(edge.v1, v);
            } else {
                int r1 = getRoot(edge.v1);
                int r2 = getRoot(edge.v2);

                if (r1 != r2) {
                    rootMap.put(r2, r1);
                } else {
                    /**
                     * since the root for v1 and v2 are the same, adding the edge could create a cycle.
                     * Therefore, skip the edge.
                     */
                    continue;
                }
            }

            mstEdges.add(edge);

            totalWeight += edge.w;
        }

        System.out.println(totalWeight);
    }
}
