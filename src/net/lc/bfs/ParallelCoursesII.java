package net.lc.bfs;

import java.util.*;

/**
 * 1494
 * BFS
 * Shortest path
 * Back-tracking (combinatorial)
 */
public class ParallelCoursesII {
    private final Map<Integer, Set<Integer>> adjMap = new HashMap<>();
    private int[] indegreeArray;
    private int[] outdegreeArray;
    private int N;
    private int K;
    private int minSemesters = Integer.MAX_VALUE;
    private int gcounter = 0;

    public int minNumberOfSemesters(int N, int[][] relations, int K) {
        adjMap.clear();
        indegreeArray = new int[N + 1];
        Arrays.fill(indegreeArray, 0);
        outdegreeArray = new int[N + 1];
        Arrays.fill(outdegreeArray, 0);
        this.N = N;
        this.K = K;
        for (int i = 1; i <= N; i++) {
            adjMap.put(i, new HashSet<>());
        }

        for (int[] array : relations) {
            int n1 = array[0];
            int n2 = array[1];
            adjMap.get(n1).add(n2);
            indegreeArray[n2]++;
        }

        for (int i = 1; i <= N; i++) {
            outdegreeArray[i] = adjMap.get(i).size();
        }


        Integer[] selected = new Integer[N+1];
        Arrays.fill(selected, 0);
        explore(selected, 0, 0);
        return minSemesters;
    }

    private void explore(Integer[] selected, int selectedCount, int numSemesters) {
        //System.out.println("selected count: " + selectedCount);
        if (selectedCount == N) {
            minSemesters = Math.min(minSemesters, numSemesters);
            return;
        }

        List<Integer> rn = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (indegreeArray[i] == 0 && selected[i] == 0) {
                rn.add(i);
            }
        }

        if (rn.size() > K) {

            List<Integer> leafNodes = new ArrayList<>();
            List<Integer> uniqueNodes = new ArrayList<>();
            for (int i : rn) {
                if (outdegreeArray[i] == 0) leafNodes.add(i);
                else uniqueNodes.add(i);
            }

            if (leafNodes.size() > 0) uniqueNodes.add(0);

            List<Integer[]> result = new ArrayList<>();
            combinationsWithZ(uniqueNodes, result, leafNodes.size(), rn.size());

            for (Integer[] array : result) {

                int idx = 0;
                for (int j = 0; j < array.length; j++) {
                    if (array[j] == 0) {
                        array[j] = leafNodes.get(idx);
                        idx++;
                    }
                }
                exploreInternal(selected, selectedCount, numSemesters, array);
            }
        } else {
            Integer[] array = rn.toArray(new Integer[rn.size()]);
            exploreInternal(selected, selectedCount, numSemesters, array);
        }
    }

    private void exploreInternal(Integer[] selected, int selectedCount, int numSemesters, Integer[] array) {
        for (int i : array) {
            selected[i] = 1;

            Set<Integer> adjNodes = adjMap.get(i);
            for (int adj : adjNodes) {
                indegreeArray[adj]--;
            }
        }

        explore(selected, selectedCount + array.length, numSemesters+1);

        for (int i : array) {
            Set<Integer> adjNodes = adjMap.get(i);
            for (int adj : adjNodes) {
                indegreeArray[adj]++;
            }

            selected[i] = 0;
        }
    }


    public void combinationsWithZ(List<Integer> input, List<Integer[]> result, int zeroCount, int totalCount) {
        combinationsWithZeros(input, result, new Stack<>(), zeroCount, 0, totalCount);
    }

    public void combinationsWithZeros(List<Integer> input, List<Integer[]> result, Stack<Integer> stack, int totalZeros, int start, int count) {
        gcounter++;
        if (stack.size() == K) {
            Integer[] res = new Integer[K];
            int index = 0;
            for (int s : stack) res[index++] = s;
            result.add(res);
            return;
        }

        for (int i = start; i < input.size(); i++) {
            int c = input.get(i);

            if (c == 0) {
                for (int occurance = 1; occurance <= totalZeros && occurance <= count; occurance++) {

                    for (int k = 0; k < occurance; k++) {
                        stack.push(c);
                    }
                    combinationsWithZeros(input, result, stack, totalZeros, i + 1, count - occurance);
                    for (int k = 0; k < occurance; k++) {
                        stack.pop();
                    }
                }
            } else {
                stack.push(input.get(i));
                combinationsWithZeros(input, result, stack, totalZeros, i + 1, count-1);
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        {
            int n = 5;
            int K = 2;

            int[][] relations = { { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };

            //System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }

        {
            int n = 4;
            int K = 2;

            int[][] relations = { { 2, 1 }, { 3, 1 }, { 1, 4 } };

            //System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }

        {
            int n = 11;
            int K = 2;

            int[][] relations = {};
            ParallelCoursesII pc = new ParallelCoursesII();
            System.out.println(pc.minNumberOfSemesters(n, relations, K));
            //System.out.println(pc.gcounter);
        }

        {
            int n = 15;
            int K = 4;

            int[][] relations = {{2,1}};
            ParallelCoursesII pc = new ParallelCoursesII();
            System.out.println(pc.minNumberOfSemesters(n, relations, K));
           //System.out.println(pc.gcounter);
        }

        {
            int n = 12;
            int K = 2;

            int[][] relations = {{1,2},{1,3},{7,5},{7,6},{4,8},{8,9},{9,10},{10,11},{11,12}};

            //System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }

        {
            int n = 5;
            int K = 3;

            int[][] relations = {{5,1},{3,1},{5,4},{4,1},{2,3}};

            //System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }

        {
            int n = 9;
            int K = 3;

            int[][] relations = {{2,4},{2,5},{1,6},{3,6},{6,7},{8,9},{3,7}};

            //System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }

        {
            int n = 12;
            int K = 3;

            int[][] relations = {{11,10},{6,3},{2,5},{9,2},{4,12},{8,7},{9,5},{6,2},{7,2},{7,4},{9,3},{11,1},{4,3}};

            System.out.println(new ParallelCoursesII().minNumberOfSemesters(n, relations, K));
        }
    }
}
