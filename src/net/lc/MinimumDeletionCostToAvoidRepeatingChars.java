package net.lc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/submissions/
 * Greedy
 */
public class MinimumDeletionCostToAvoidRepeatingChars {
    public int minCost2(String s, int[] cost) {
        char[] array = s.toCharArray();

        List<int[]> ranges = new ArrayList<>();

        int left = 0;
        int right = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i-1]) {
                right++;
            } else {

                ranges.add(new int[] {left, right});
                left = i;
                right = i;
            }
        }

        ranges.add(new int[] {left, right});

        int removecost = 0;
        for (int[] range : ranges) {
            int l = range[0];
            int r = range[1];

            if (l == r) continue;

            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
            for (int i = l; i <= r; i++) {
                pq.add(cost[i]);
            }
            pq.remove();
            while (!pq.isEmpty()) removecost += pq.remove().intValue();
        }
        return removecost;
    }

    public int minCost(String s, int[] cost) {
        char[] array = s.toCharArray();


        int removecost = 0;
        int cursum = cost[0];
        int curMax = cost[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i-1]) {
                cursum += cost[i];
                curMax = Math.max(curMax, cost[i]);
            } else {
                cursum -= curMax;
                removecost += cursum;

                cursum = cost[i];
                curMax = cost[i];
            }
        }

        cursum -= curMax;
        removecost += cursum;
        return removecost;
    }

    public static void main(String[] args) {
        {
            String s = "abaac";
            int[] cost = { 1, 2, 3, 4, 5 };
            System.out.println(new MinimumDeletionCostToAvoidRepeatingChars().minCost(s, cost));
        }
        {
            String s = "anc";
            int[] cost = { 1, 2, 3};
            System.out.println(new MinimumDeletionCostToAvoidRepeatingChars().minCost(s, cost));
        }
        {
            String s = "aabaa";
            int[] cost = { 1, 2, 3, 4,1 };
            System.out.println(new MinimumDeletionCostToAvoidRepeatingChars().minCost(s, cost));
        }
    }
}
