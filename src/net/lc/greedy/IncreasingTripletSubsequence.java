package net.lc.greedy;

/**
 * 334
 * Greedy
 */
public class IncreasingTripletSubsequence {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) return false;

        int smallest = Integer.MAX_VALUE;
        int middle = Integer.MAX_VALUE;

        for (int val : nums) {
            if (val <= smallest) {
                smallest = val;
            } else if (val <= middle) {
                middle = val;
            } else {
                /**
                 * found smallest and middle before, so current val completes the triplet
                 */
                return true;
            }
        }
        return false;
    }
}
