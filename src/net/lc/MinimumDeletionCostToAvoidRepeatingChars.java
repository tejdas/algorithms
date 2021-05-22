package net.lc;

/**
 * 1578
 * https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/submissions/
 * Greedy
 */
public class MinimumDeletionCostToAvoidRepeatingChars {
    public int minCost(String s, int[] cost) {
        char[] array = s.toCharArray();

        int removecost = 0;
        int cursum = cost[0];
        int curMax = cost[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i-1]) {
                /**
                 * Repeating sequence continues.
                 *
                 * assume that all consecutive identical numbers will be removed
                 * and keep adding to cursum
                 * Also, keep track of the curmqx that need to be removed
                 * once we encounter another number.
                 */
                cursum += cost[i];
                curMax = Math.max(curMax, cost[i]);
            } else {
                /**
                 * we encountered another number. Now remove the curmax from cursum
                 * and then add to removecost.
                 * Also, reset cursum and curmax, as this could be the start of another
                 * repeating sequence.
                 */
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
