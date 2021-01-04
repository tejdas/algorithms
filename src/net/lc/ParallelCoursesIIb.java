package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/parallel-courses-ii
 * 66 out of 67 tests were passing.
 */
public class ParallelCoursesIIb {
    private final Map<Integer, Set<Integer>> adjMap = new HashMap<>();
    private int[] indegreeArray;
    private int[] depthArray;
    private int N;

    class NodeWeight implements Comparable<NodeWeight> {
        private final int idx;
        private final int count;
        private final int depth;

        public NodeWeight(int idx) {
            this.idx = idx;
            this.count = computeCount(idx);
            this.depth = depthArray[idx];
        }

        private int computeCount(int idx) {
            return adjMap.get(idx).size();
        }

        @Override
        public int compareTo(NodeWeight o) {
            if (o.depth == this.depth) {
                return Integer.compare(o.count, this.count);
            }
            return Integer.compare(o.depth, this.depth);
        }
    }

    public int minNumberOfSemesters(int N, int[][] relations, int K) {
        adjMap.clear();
        indegreeArray = new int[N + 1];
        Arrays.fill(indegreeArray, 0);
        depthArray = new int[N + 1];
        Arrays.fill(depthArray, 0);
        this.N = N;
        for (int i = 1; i <= N; i++) {
            adjMap.put(i, new HashSet<>());
        }

        for (int[] array : relations) {
            int n1 = array[0];
            int n2 = array[1];
            adjMap.get(n1).add(n2);
            indegreeArray[n2]++;
        }

        performDfs();
        //System.out.println(Arrays.toString(depthArray));

        PriorityQueue<NodeWeight> activeQ = new PriorityQueue<>();

        for (int i = 1; i <= N; i++) {
            if (0 == indegreeArray[i]) {
                activeQ.add(new NodeWeight(i));
            }
        }

        int minSemesters = 0;
        int count = 0;

        Queue<Integer> passiveQ = new LinkedList<>();

        while (true) {
            while (!activeQ.isEmpty()) {
                boolean flag = false;
                NodeWeight node = activeQ.remove();
                System.out.println("processed " + node.idx);
                count++;

                for (int adj : adjMap.get(node.idx)) {
                    indegreeArray[adj]--;
                    if (0 == indegreeArray[adj]) {
                        passiveQ.add(adj);
                    }
                }

                if (count == K || activeQ.isEmpty()) {
                    minSemesters++;
                    count = 0;
                    flag = true;
                    System.out.println("done sem");
                }

                if (flag) {
                    while (!passiveQ.isEmpty()) {
                        activeQ.add(new NodeWeight(passiveQ.remove()));
                    }
                }
            }

            if (count > 0)
                minSemesters++;

            if (passiveQ.isEmpty()) {
                break;
            }

            while (!passiveQ.isEmpty()) {
                activeQ.add(new NodeWeight(passiveQ.remove()));
            }
        }

        //System.out.println(Arrays.toString(depthArray));
        return minSemesters;
    }

    private void performDfs() {
        Set<Integer> startNodes = new HashSet<>();
        for (int i = 1; i <= N; i++) {
            if (0 == indegreeArray[i]) {
                startNodes.add(i);
            }
        }

        boolean[] visited = new boolean[N+1];
        Arrays.fill(visited, false);
        for (int i : startNodes) {
            dfs(visited, i);
        }
    }

    private void dfs(boolean[] visited, int curNode) {
        visited[curNode] = true;

        Set<Integer> adjNodes = adjMap.get(curNode);
        int depth = 1;
        for (int adjNode : adjNodes) {
            if (!visited[adjNode]) {
                dfs(visited, adjNode);
            }
            depth = Math.max(depth, 1 + depthArray[adjNode]);
        }
        depthArray[curNode] = depth;
        return;
    }
}
