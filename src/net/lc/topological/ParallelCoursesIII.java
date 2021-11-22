package net.lc.topological;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 2050
 * Topological Sort
 * Greedy
 */
public class ParallelCoursesIII {

    private List[] successors;
    private int[] predCount;

    private int[] latestCompletionTime;
    public int minimumTime(int n, int[][] relations, int[] time) {
        successors = new List[n];
        predCount = new int[n];
        latestCompletionTime = new int[n];
        for (int i = 0; i < time.length; i++) {
            latestCompletionTime[i] = time[i];
        }

        for (int[] rel : relations) {
            int fromIndex = rel[0]-1;
            int toIndex = rel[1]-1;

            if (successors[fromIndex] == null) {
                successors[fromIndex] = new ArrayList<Integer>();
            }
            successors[fromIndex].add(toIndex);
            predCount[toIndex]++;
        }

        List<Integer> topoList = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < predCount.length; i++) {
            if (predCount[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            int node = queue.remove();
            topoList.add(node);

            List<Integer> succs = successors[node];

            if (succs != null) {
                for (int succ : succs) {
                    predCount[succ]--;
                    if (predCount[succ] == 0)
                        queue.add(succ);
                }
            }
        }

        int result = 0;

        /**
         * traverse the jobs in topological order. Use greedy approach to compute latest completion time.
         */
        for (int i = 0; i < topoList.size(); i++) {
            int cur = topoList.get(i);
            List<Integer> succs = successors[cur];

            if (succs != null) {
                for (int succ : succs) {
                    latestCompletionTime[succ] = Math.max(latestCompletionTime[succ], latestCompletionTime[cur] + time[succ]);
                }
            }

            result = Math.max(result, latestCompletionTime[cur]);
        }

        return result;
    }

    public static void main(String[] args) {
        {
            int[][] rels = { { 1, 3 }, { 2, 3 } };
            System.out.println(new ParallelCoursesIII().minimumTime(3, rels, new int[] { 3, 2, 5 }));
        }

        {
            int[][] rels = { { 1, 5}, { 2, 5 }, {3,5},{3,4},{4,5} };
            System.out.println(new ParallelCoursesIII().minimumTime(5, rels, new int[] { 1,2,3,4,5 }));
        }
    }
}
