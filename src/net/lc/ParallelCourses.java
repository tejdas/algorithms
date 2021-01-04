package net.lc;

import java.util.*;

/**
 * BFS
 * Topological
 * Actitve/Passive Queue
 */
class ParallelCourses {
    private final Map<Integer, Set<Integer>> adjMap = new HashMap<>();
    private int[] indegreeArray;

    public int minimumSemesters(int N, int[][] relations) {
        if (relations == null || relations.length==0) return 0;

        adjMap.clear();
        indegreeArray = new int[N+1];
        Arrays.fill(indegreeArray, 0);

        for (int[] array : relations) {
            int n1 = array[0];
            int n2 = array[1];
            Set<Integer> set = adjMap.computeIfAbsent(n1, k-> new HashSet<>());
            set.add(n2);
            indegreeArray[n2]++;
        }

        Queue<Integer> activeQ = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            if (0 == indegreeArray[i]) {
                activeQ.add(i);
            }
        }

        int minSemesters = 0;

        Queue<Integer> passiveQ = new LinkedList<>();

        while (true) {
            if (!activeQ.isEmpty()) minSemesters++;

            while (!activeQ.isEmpty()) {
                int node = activeQ.remove();
                if (!adjMap.containsKey(node)) {
                    continue;
                }

                for (int adj : adjMap.get(node)) {
                    indegreeArray[adj]--;
                    if (0 == indegreeArray[adj]) {
                        passiveQ.add(adj);
                    }
                }
            }

            if (passiveQ.isEmpty()) {
                break;
            }

            Queue<Integer> temp = activeQ;
            activeQ = passiveQ;
            passiveQ = temp;
        }

        for (int i = 1; i <= N; i++) {
            if (indegreeArray[i] > 0) {
                return -1;
            }
        }

        return minSemesters;
    }
}
