package algo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class MaxFlowGraph {
    private static class MaxFlowDEdge {
        public MaxFlowDEdge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.currentFlow = 0;
        }

        int from;

        int to;

        int capacity;

        int currentFlow;

        public int residualCapacity() {
            return capacity - currentFlow;
        }

        public boolean hasResidualCapacity() {
            return (capacity - currentFlow > 0);
        }

        public int augmentedCapacity() {
            return currentFlow;
        }

        public boolean hasAugmentedCapacity() {
            return (currentFlow > 0);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (obj == this)
                return true;

            if (!(obj instanceof MaxFlowDEdge))
                return false;

            MaxFlowDEdge that = (MaxFlowDEdge) obj;
            return (this.from == that.from && this.to == that.to && this.capacity == that.capacity);
        }

        @Override
        public String toString() {
            return String.format("From %d To %d Capacity %d Flow %d", from, to,
                    capacity, currentFlow);
        }
    }

    private static class EdgeInfo implements Comparable<EdgeInfo> {
        public EdgeInfo(MaxFlowDEdge edge, boolean isOut) {
            super();
            this.edge = edge;
            this.isOut = isOut;
            this.weight = isOut ? edge.residualCapacity() : edge.augmentedCapacity();
        }

        final MaxFlowDEdge edge;

        final int weight;

        final boolean isOut;

        @Override
        public int compareTo(EdgeInfo arg0) {
            if (arg0.isOut != this.isOut)
                return 0;

            if (isOut) {
                if (weight > arg0.weight)
                    return -1;
                else if (weight < arg0.weight)
                    return 1;
                else
                    return 0;
            } else {
                if (weight < arg0.weight)
                    return -1;
                else if (weight > arg0.weight)
                    return 1;
                else
                    return 0;
            }
        }
    }

    private static class MinCapacityOutEdgeInfo implements
            Comparable<MinCapacityOutEdgeInfo> {
        public MinCapacityOutEdgeInfo(MaxFlowDEdge edge) {
            super();
            this.edge = edge;
            this.weight = edge.residualCapacity();
        }

        final MaxFlowDEdge edge;

        final int weight;

        @Override
        public int compareTo(MinCapacityOutEdgeInfo arg0) {
            if (weight < arg0.weight)
                return -1;
            else if (weight > arg0.weight)
                return 1;
            else
                return 0;
        }
    }

    private final int vertexCount;

    private final int source;

    private final int target;

    private final Map<Integer, List<MaxFlowDEdge>> adjacentEdgesOut = new HashMap<>();

    private final Map<Integer, List<MaxFlowDEdge>> adjacentEdgesIn = new HashMap<>();

    private static int count = 0;

    public MaxFlowGraph(int vertexCount, int source, int target) {
        this.vertexCount = vertexCount;
        this.source = source;
        this.target = target;
        for (int i = 0; i < vertexCount; i++) {
            adjacentEdgesOut.put(i, new ArrayList<>());
            adjacentEdgesIn.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int capacity) {
        if (from >= vertexCount || to >= vertexCount) {
            throw new IllegalArgumentException("Invalid vertex: ");
        }
        MaxFlowDEdge edge = new MaxFlowDEdge(from, to, capacity);
        adjacentEdgesOut.get(from).add(edge);
        adjacentEdgesIn.get(to).add(edge);
    }

    private int findPath(int curNode, int minResidualAlongPath,
            Stack<MaxFlowDEdge> path) {
        count++;
        PriorityQueue<EdgeInfo> pq = new PriorityQueue<>();

        for (MaxFlowDEdge outEdge : adjacentEdgesOut.get(curNode)) {
            pq.offer(new EdgeInfo(outEdge, true));
        }
        for (MaxFlowDEdge inEdge : adjacentEdgesIn.get(curNode)) {
            pq.offer(new EdgeInfo(inEdge, false));
        }

        while (!pq.isEmpty()) {
            EdgeInfo eInfo = pq.poll();
            if (eInfo.isOut) {
                MaxFlowDEdge outEdge = eInfo.edge;
                if (0 == outEdge.residualCapacity())
                    continue;
                int minResidualAlongPathUpdated = (minResidualAlongPath < outEdge.residualCapacity()) ? minResidualAlongPath : outEdge.residualCapacity();
                if (outEdge.to == target) {
                    path.push(outEdge);
                    outEdge.currentFlow += minResidualAlongPathUpdated;
                    return minResidualAlongPathUpdated;
                } else if (!path.contains(outEdge)) {
                    path.push(outEdge);
                    int res = findPath(outEdge.to, minResidualAlongPathUpdated,
                            path);
                    if (res <= 0) {
                        path.pop();
                        continue;
                    } else {
                        outEdge.currentFlow += res;
                        return res;
                    }
                }
            } else {
                MaxFlowDEdge inEdge = eInfo.edge;

                if (!inEdge.hasAugmentedCapacity())
                    continue;
                System.out.println("Augmented capacity: " + inEdge.augmentedCapacity());
                int minResidualAlongPathUpdated = (minResidualAlongPath < inEdge.augmentedCapacity()) ? minResidualAlongPath : inEdge.augmentedCapacity();
                if (!path.contains(inEdge)) {
                    path.push(inEdge);
                    int res = findPath(inEdge.from,
                            minResidualAlongPathUpdated, path);
                    if (res <= 0) {
                        path.pop();
                        continue;
                    } else {
                        inEdge.currentFlow -= res;
                        System.out.println("Removed flow: " + res + "    Edge: " + inEdge.toString());
                        return res;
                    }
                }
            }
        }

        return 0;
    }

    public void computeMaxFlow() {
        PriorityQueue<MinCapacityOutEdgeInfo> verticesToExplore = new PriorityQueue<>();
        Map<Integer, MaxFlowDEdge> adjVertices = new HashMap<>();
        for (MaxFlowDEdge edge : adjacentEdgesOut.get(source)) {
            adjVertices.put(edge.to, edge);
            verticesToExplore.add(new MinCapacityOutEdgeInfo(edge));
        }

        List<Integer> verticesExplored = new ArrayList<>();
        while (!verticesToExplore.isEmpty()) {
            MinCapacityOutEdgeInfo eInfo = verticesToExplore.poll();
            Integer nextV = eInfo.edge.to;

            MaxFlowDEdge maxEdge = adjVertices.get(nextV);
            if (!maxEdge.hasResidualCapacity()) {
                verticesToExplore.remove(nextV);
                verticesExplored.add(nextV);
                continue;
            }
            while (true) {
                Stack<MaxFlowDEdge> path = new Stack<>();
                path.add(maxEdge);
                int min = findPath(maxEdge.to, maxEdge.residualCapacity(), path);
                if (min <= 0) {
                    verticesToExplore.remove(nextV);
                    break;
                } else {
                    maxEdge.currentFlow += min;
                    if (!maxEdge.hasResidualCapacity()) {
                        verticesToExplore.remove(nextV);
                        verticesExplored.add(nextV);
                        break;
                    }
                }
            }

            Set<Integer> keysToRemove = new HashSet<>();
            for (Integer key : verticesExplored) {
                MaxFlowDEdge e = adjVertices.get(key);
                if (e.hasResidualCapacity()) {
                    verticesToExplore.add(new MinCapacityOutEdgeInfo(e));
                    keysToRemove.add(key);
                }
            }

            for (Integer key : keysToRemove) {
                verticesExplored.remove(key);
            }
        }
    }

    public void printEdges() {
        System.out.println("----------------------------------");
        for (int v = 0; v < vertexCount; v++) {
            for (MaxFlowDEdge e : adjacentEdgesOut.get(v)) {
                System.out.println(e.toString());
            }
        }
        System.out.println("----------------------------------");
        System.out.println("Count: " + count);
    }

    public static void main(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(8, 0, 7);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 5);
        g.addEdge(0, 3, 15);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 4, 9);
        g.addEdge(1, 5, 15);
        g.addEdge(2, 3, 4);
        g.addEdge(2, 5, 8);
        g.addEdge(3, 6, 16);

        g.addEdge(4, 5, 15);
        g.addEdge(4, 7, 10);
        g.addEdge(5, 6, 15);
        g.addEdge(5, 7, 10);
        g.addEdge(6, 2, 6);
        g.addEdge(6, 7, 10);

        g.computeMaxFlow();
        g.printEdges();
    }

    public static void main2(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(7, 0, 6);
        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 3, 5);
        g.addEdge(2, 4, 4);
        g.addEdge(3, 6, 2);
        g.addEdge(4, 5, 2);
        g.addEdge(5, 6, 3);

        g.computeMaxFlow();
        g.printEdges();
    }

    public static void main3(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(7, 0, 6);
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
        g.printEdges();
    }

    public static void main4(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(6, 0, 5);
        g.addEdge(0, 1, 7);
        g.addEdge(0, 2, 8);
        g.addEdge(0, 3, 2);
        g.addEdge(1, 5, 5);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 3);
        g.addEdge(3, 4, 3);
        g.addEdge(4, 5, 6);

        g.computeMaxFlow();
        g.printEdges();
    }

    public static void main5(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(6, 0, 5);
        g.addEdge(0, 1, 16);
        g.addEdge(0, 2, 13);
        g.addEdge(1, 2, 10);
        g.addEdge(1, 3, 12);
        g.addEdge(2, 1, 4);
        g.addEdge(2, 4, 14);
        g.addEdge(3, 2, 9);
        g.addEdge(3, 5, 20);
        g.addEdge(4, 3, 7);
        g.addEdge(4, 5, 4);

        g.computeMaxFlow();
        g.printEdges();
    }

    public static void main6(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(6, 0, 5);
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
        g.printEdges();
    }

    public static void main7(String[] args) {
        MaxFlowGraph g = new MaxFlowGraph(6, 0, 5);
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
        g.printEdges();
    }
}
