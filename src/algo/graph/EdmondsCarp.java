package algo.graph;

import java.util.*;

public class EdmondsCarp {
    private static int gId = 0;

    static class DEdge {
        int id;
        int flow;
        int capacity;
        char from;
        char to;

        public DEdge(char from, char to, int capacity) {
            this.id = gId++;
            this.from = from;
            this.to = to;
            flow = 0;
            this.capacity = capacity;
        }

        public void addFlow(int delta) {
            this.flow += delta;
        }

        public boolean hasCapacity() {
            return (flow < capacity);
        }

        public int remainingCapacity() {
            return (capacity-flow);
        }

        public boolean hasResidualCapacity() {
            return (flow > 0);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DEdge{");
            sb.append("id=").append(id);
            sb.append(", flow=").append(flow);
            sb.append(", capacity=").append(capacity);
            sb.append(", from=").append(from);
            sb.append(", to=").append(to);
            sb.append('}');
            return sb.toString();
        }
    }

    private final Map<Integer, DEdge> edgeMap = new HashMap<>();
    private final Set<Character> vertices = new HashSet<>();

    private final Map<Character, List<Integer>> outgoing = new HashMap<>();
    private final Map<Character, List<Integer>> incoming = new HashMap<>();

    private char source;
    private char target;

    private void addEdge(char from, char to, int capacity) {
        vertices.add(from);
        vertices.add(to);
        DEdge edge = new DEdge(from, to, capacity);
        edgeMap.put(edge.id, edge);

        List<Integer> outV = outgoing.computeIfAbsent(from, k-> new ArrayList<>());
        outV.add(edge.id);

        List<Integer> inV = incoming.computeIfAbsent(to, k-> new ArrayList<>());
        inV.add(edge.id);
    }

    public EdmondsCarp(char source, char target) {
        this.source = source;
        this.target = target;

        outgoing.put(source, new ArrayList<>());
        incoming.put(target, new ArrayList<>());
    }

    static class Pair {
        boolean isForwardEdge;
        DEdge edge;

        public Pair(boolean isForwardEdge, DEdge edge) {
            this.isForwardEdge = isForwardEdge;
            this.edge = edge;
        }
    }

    private int findAugmentedFlow() {

        Map<Character, Integer> edgeToMap = new HashMap<>();

        Queue<Pair> queue = new LinkedList<>();

        List<Integer> outV = outgoing.get(source);

        for (int eId : outV) {
            DEdge edge = edgeMap.get(eId);
            if (edge.hasCapacity()) queue.add(new Pair(true, edge));
        }

        if (queue.isEmpty()) return -1;

        boolean reachedTarget = false;

        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            if (cur.isForwardEdge) {
                DEdge curEdge = cur.edge;

                if (!edgeToMap.containsKey(curEdge.to)) {
                    edgeToMap.put(curEdge.to, curEdge.id);

                    if (curEdge.to == target) {
                        reachedTarget = true;
                        break;
                    }

                    outV = outgoing.get(cur.edge.to);

                    for (int eId : outV) {
                        DEdge edge = edgeMap.get(eId);
                        if (edge.hasCapacity()) queue.add(new Pair(true, edge));
                    }
                }
            }
        }

        if (!reachedTarget) {
            return -1;
        }

        LinkedList<DEdge> edgesTraversed = new LinkedList<>();

        int minAugmented = Integer.MAX_VALUE;

        char curNode = target;
        while (curNode != source) {
            int edgeId = edgeToMap.get(curNode);
            DEdge edge = edgeMap.get(edgeId);
            minAugmented = Math.min(minAugmented, edge.remainingCapacity());
            edgesTraversed.addFirst(edge);

            curNode = edge.from;
        }

        if (minAugmented != Integer.MAX_VALUE) {
            for (DEdge edge : edgesTraversed) {
                edge.addFlow(minAugmented);
                System.out.println(edge);
            }
        }

        return minAugmented;
    }

    public void computeMaxFlow() {

        int maxFlow = 0;
        for (int i = 0; i < 5; i++) {
            int augmentedFlow = findAugmentedFlow();
            System.out.println(augmentedFlow);
            if (augmentedFlow == -1) break;
            maxFlow += augmentedFlow;
        }

        System.out.println("maxFlow: " + maxFlow);
    }

    public static void main(String[] args) {
        EdmondsCarp ec = new EdmondsCarp('S', 'T');
        ec.addEdge('S', 'A', 10);
        ec.addEdge('S', 'C', 10);
        ec.addEdge('A', 'B', 4);
        ec.addEdge('A', 'C', 2);
        ec.addEdge('A', 'D', 8);
        ec.addEdge('C', 'D', 9);
        ec.addEdge('D', 'B', 6);
        ec.addEdge('D', 'T', 10);
        ec.addEdge('B', 'T', 10);

        ec.computeMaxFlow();
    }
}
