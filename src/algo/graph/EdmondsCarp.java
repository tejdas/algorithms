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
            return (capacity - flow);
        }

        public boolean hasResidualCapacity() {
            return (flow > 0);
        }

        @Override
        public String toString() {

            return String.format("%c -> %c,   %d / %d", from, to, flow, capacity);
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

        List<Integer> outV = outgoing.computeIfAbsent(from, k -> new ArrayList<>());
        outV.add(edge.id);

        List<Integer> inV = incoming.computeIfAbsent(to, k -> new ArrayList<>());
        inV.add(edge.id);
    }

    private void addEdge(int from, int to, int capacity) {
        char cFrom = Character.forDigit(from, 10);
        char cTo = Character.forDigit(to, 10);
        addEdge(cFrom, cTo, capacity);
    }

    public EdmondsCarp(char source, char target) {
        gId = 0;
        this.source = source;
        this.target = target;

        outgoing.put(source, new ArrayList<>());
        incoming.put(target, new ArrayList<>());
    }

    static class EdgeTraversalInfo {
        boolean isForwardEdge;
        char fromNode;
        DEdge edge;

        public EdgeTraversalInfo(boolean isForwardEdge, char fromNode, DEdge edge) {
            this.isForwardEdge = isForwardEdge;
            this.fromNode = fromNode;
            this.edge = edge;
        }
    }

    private int findAugmentedFlow() {

        Map<Character, EdgeTraversalInfo> edgeToMap = new HashMap<>();

        Queue<EdgeTraversalInfo> queue = new LinkedList<>();

        List<Integer> outEdges = outgoing.get(source);

        for (int eId : outEdges) {
            DEdge edge = edgeMap.get(eId);
            if (edge.hasCapacity()) {
                queue.add(new EdgeTraversalInfo(true, source, edge));
            }
        }

        if (queue.isEmpty())
            return -1;

        boolean reachedTarget = false;

        while (!queue.isEmpty()) {
            EdgeTraversalInfo cur = queue.poll();

            char curNode = (cur.isForwardEdge) ? cur.edge.to : cur.edge.from;

            if (!edgeToMap.containsKey(curNode)) {
                edgeToMap.put(curNode, cur);

                if (curNode == target) {
                    reachedTarget = true;
                    break;
                }

                outEdges = outgoing.get(curNode);

                boolean foundForwardCapacity = false;

                for (int eId : outEdges) {
                    DEdge edge = edgeMap.get(eId);
                    if (edge.hasCapacity()) {
                        queue.add(new EdgeTraversalInfo(true, curNode, edge));
                        foundForwardCapacity = true;
                    }
                }

                if (!foundForwardCapacity) {
                    /**
                     * inspect reverse (residual) edges
                     */
                    List<Integer> inEdges = incoming.get(curNode);
                    if (inEdges == null || inEdges.isEmpty())
                        continue;

                    for (int eId : inEdges) {
                        DEdge edge = edgeMap.get(eId);
                        if (edge.from == source)
                            continue;

                        if (edge.hasResidualCapacity()) {
                            queue.add(new EdgeTraversalInfo(false, curNode, edge));
                        }
                    }
                }
            }
        }

        if (!reachedTarget) {
            return -1;
        }

        LinkedList<EdgeTraversalInfo> edgesTraversed = new LinkedList<>();

        int minAugmented = Integer.MAX_VALUE;

        char curNode = target;
        while (curNode != source) {
            EdgeTraversalInfo edgeInfo = edgeToMap.get(curNode);
            if (edgeInfo.isForwardEdge) {
                minAugmented = Math.min(minAugmented, edgeInfo.edge.remainingCapacity());
            }
            edgesTraversed.addFirst(edgeInfo);
            curNode = edgeInfo.fromNode;
        }

        if (minAugmented != Integer.MAX_VALUE) {
            for (EdgeTraversalInfo edgeTraversalInfo : edgesTraversed) {
                if (edgeTraversalInfo.isForwardEdge) {
                    edgeTraversalInfo.edge.addFlow(minAugmented);
                } else {
                    edgeTraversalInfo.edge.addFlow(-minAugmented);
                }
                System.out.println(edgeTraversalInfo.edge);
            }
        }

        return minAugmented;
    }

    public void computeMaxFlow() {

        int maxFlow = 0;
        while (true) {
            int augmentedFlow = findAugmentedFlow();
            System.out.println(augmentedFlow);
            if (augmentedFlow == -1)
                break;
            maxFlow += augmentedFlow;
        }

        System.out.println("maxFlow: " + maxFlow);
    }

    public static void main(String[] args) {
        {
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

        {
            EdmondsCarp g = new EdmondsCarp('0', '7');

            g.addEdge('0', '1', 10);
            g.addEdge('0', '2', 5);
            g.addEdge('0', '3', 15);
            g.addEdge('1', '2', 4);
            g.addEdge('1', '4', 9);
            g.addEdge('1', '5', 15);
            g.addEdge('2', '3', 4);
            g.addEdge('2', '5', 8);
            g.addEdge('3', '6', 16);

            g.addEdge('4', '5', 15);
            g.addEdge('4', '7', 10);
            g.addEdge('5', '6', 15);
            g.addEdge('5', '7', 10);
            g.addEdge('6', '2', 6);
            g.addEdge('6', '7', 10);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('S', 'T');

            g.addEdge('S', '0', 10);
            g.addEdge('S', '1', 10);
            g.addEdge('0', '2', 25);
            g.addEdge('3', '0', 6);
            g.addEdge('1', '3', 15);

            g.addEdge('3', 'T', 10);
            g.addEdge('2', 'T', 10);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('0', '6');

            g.addEdge(0, 1, 3);
            g.addEdge(0, 2, 1);
            g.addEdge(1, 3, 3);
            g.addEdge(2, 3, 5);
            g.addEdge(2, 4, 4);
            g.addEdge(3, 6, 2);
            g.addEdge(4, 5, 2);
            g.addEdge(5, 6, 3);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('0', '6');

            g.addEdge(0, 1, 3);
            g.addEdge(0, 2, 3);
            g.addEdge(0, 3, 4);
            g.addEdge(1, 4, 2);
            g.addEdge(2, 1, 10);
            g.addEdge(2, 4, 1);
            g.addEdge(3, 5, 5);
            g.addEdge(4, 3, 1);
            g.addEdge(4, 5, 1);
            g.addEdge(4, 6, 2);
            g.addEdge(5, 6, 5);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('0', '5');

            g.addEdge(0, 1, 7);
            g.addEdge(0, 2, 8);
            g.addEdge(0, 3, 2);
            g.addEdge(1, 5, 5);
            g.addEdge(2, 4, 4);
            g.addEdge(2, 5, 3);
            g.addEdge(3, 4, 3);
            g.addEdge(4, 5, 6);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('0', '5');

            g.addEdge(0, 1, 10);
            g.addEdge(0, 2, 10);
            g.addEdge(1, 2, 2);
            g.addEdge(1, 3, 4);
            g.addEdge(1, 4, 8);
            g.addEdge(2, 4, 9);
            g.addEdge(3, 5, 10);
            g.addEdge(4, 3, 6);
            g.addEdge(4, 5, 10);

            g.computeMaxFlow();
        }

        {
            EdmondsCarp g = new EdmondsCarp('0', '5');
            g.addEdge(0, 1, 3);
            g.addEdge(0, 2, 3);
            g.addEdge(0, 3, 2);
            g.addEdge(1, 4, 4);
            g.addEdge(2, 3, 1);
            g.addEdge(2, 5, 2);
            g.addEdge(3, 1, 1);
            g.addEdge(3, 5, 2);
            g.addEdge(4, 3, 1);
            g.addEdge(4, 5, 1);

            g.computeMaxFlow();
        }
    }

}
