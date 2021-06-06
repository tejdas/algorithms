package net.lc.heap;

import java.util.*;

/**
 * 218
 * PriorityQueue
 * Heap
 */
public class Skyline {
    static class BuildingEdge implements Comparable<BuildingEdge> {
        int id;
        int x;
        boolean isLeft;
        int height;

        public BuildingEdge(int id, int x, boolean isLeft, int height) {
            this.id = id;
            this.x = x;
            this.isLeft = isLeft;
            this.height = height;
        }

        @Override
        public int compareTo(BuildingEdge o) {
            if (this.x != o.x) {
                return Integer.compare(this.x, o.x);
            }

            if (!this.isLeft) {
                return -1;
            } else if (o.isLeft) {
                return 1;
            }

            return 0;
        }
    }

    static class BuildingHeight implements Comparable<BuildingHeight> {
        int id;
        int height;

        public BuildingHeight(int id, int height) {
            this.id = id;
            this.height = height;
        }

        @Override
        public int compareTo(BuildingHeight o) {
            return Integer.compare(o.height, this.height);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            BuildingHeight that = (BuildingHeight) o;

            if (id != that.id)
                return false;
            return height == that.height;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + height;
            return result;
        }
    }

    private PriorityQueue<BuildingEdge> pq = new PriorityQueue<>();
    private SortedSet<BuildingHeight> pqHeight = new TreeSet<>();
    private List<int[]> output = new ArrayList<>();

    public List<int[]> getSkyline(int[][] buildings) {
        pqHeight.clear();

        if (buildings == null || buildings.length == 0) {
            return output;
        }

        for (int i = 0; i < buildings.length; i++) {
            int leftEdge = buildings[i][0];
            int rightEdge = buildings[i][1];
            int height = buildings[i][2];

            pq.add(new BuildingEdge(i, leftEdge, true, height));
            pq.add(new BuildingEdge(i, rightEdge, false, height));
        }

        int buildingNestedDepth = 0;

        while (!pq.isEmpty()) {
            BuildingEdge edge = pq.poll();

            if (edge.isLeft) {
                if (buildingNestedDepth == 0) {
                    addOutput(edge.x, edge.height);
                } else {
                    if (!pqHeight.isEmpty() && edge.height > pqHeight.first().height) {
                        addOutput(edge.x, edge.height);
                    }
                }

                buildingNestedDepth++;
                pqHeight.add(new BuildingHeight(edge.id, edge.height));

            } else {
                buildingNestedDepth--;
                if (buildingNestedDepth == 0) {
                    if (!isNextBuildingAdjacent(edge.x)) {
                        addOutput(edge.x, 0);
                    }
                    pqHeight.clear();
                } else {
                    pqHeight.remove(new BuildingHeight(edge.id, edge.height));
                    if (!pqHeight.isEmpty() && edge.height > pqHeight.first().height) {
                        addOutput(edge.x, pqHeight.first().height);
                    }
                }
            }

        }
        return output;
    }

    private void addOutput(int x, int height) {
        if (output.isEmpty()) {
            output.add(new int[] {x, height});
            return;
        }
        int[] lastArray = output.get(output.size()-1);
        if (height == lastArray[1]) {
            return;
        }

        if (x == lastArray[0]) {
            if (height > lastArray[1] || height==0) {
                output.remove(output.size()-1);
            } else {
                return;
            }
        }
        output.add(new int[] {x, height});
    }

    private boolean isNextBuildingAdjacent(int x) {
        return !pq.isEmpty() && (pq.peek().x == x);
    }
}
