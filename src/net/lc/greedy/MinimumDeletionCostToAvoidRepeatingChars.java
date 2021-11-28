package net.lc.greedy;

/**
 * 1578
 * Return the minimum cost of deletions such that there are no two identical letters next to each other.
 * https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/submissions/
 * Greedy
 */
public class MinimumDeletionCostToAvoidRepeatingChars {
    /**
     * Whenever we get a series of repeating chars, we need to remove all but one.
     * Keep track of the runningSum + the max cost of one char deletion.
     * The cost will then be: runningSum - maxSingleCharCost
     * Do this for all repeating series of chars.
     *
     * @param s
     * @param cost
     * @return
     */
    public int minCost(String s, int[] cost) {
        char[] array = s.toCharArray();

        int removecost = 0;
        int cursum = cost[0];
        int curMax = cost[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i-1]) {
                /**
                 * Repeating sequence continues.
                 * In a repeating sequence: xyyyyyyz, we will remove all y's except one.
                 * The one we need to keep is the one that has max deletion cost.
                 * So, keep track of curmax.
                 *
                 * Assume that all consecutive identical numbers will be removed
                 * and keep adding to cursum
                 * Also, keep track of the curmqx that does not need to be removed
                 * once we encounter another number.
                 */
                cursum += cost[i];
                curMax = Math.max(curMax, cost[i]);
            } else {
                /**
                 * we encountered another number. Now remove the curmax from cursum
                 * and then add cursum to removecost.
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
