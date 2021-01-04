package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/target-sum/
 */
public class TargetSum {
    static class Pair {
        int target;
        int index;

        public Pair(int target, int index) {
            this.target = target;
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair pair = (Pair) o;

            if (target != pair.target)
                return false;
            return index == pair.index;
        }

        @Override
        public int hashCode() {
            int result = target;
            result = 31 * result + index;
            return result;
        }
    }

    private Map<Pair, Integer> res = new HashMap<>();
    private int[] input;
    public int findTargetSumWays(int[] nums, int S) {

        if (S > 1000) return 0;
        this.input = nums;
        res.clear();
        return computeTargetSum(S, nums.length-1);
    }

    private int computeTargetSum(int target, int index) {
        Pair p = new Pair(target, index);

        if (res.containsKey(p)) return res.get(p);

        int val = input[index];

        if (index == 0) {
            if (val == target && val == 0) {
                res.put(p, 2);
            } else if (val == target) {
                res.put(p, 1);
            } else if (val == -target) {
                res.put(p, 1);
            } else {
                res.put(p, 0);
            }
        } else {

            if (val != 0) {
                int count = computeTargetSum(target + val, index - 1) + computeTargetSum(target - val, index - 1);
                res.put(p, count);
            } else {
                int count = computeTargetSum(target, index - 1);
                res.put(p, count*2);
            }
        }
        return res.get(p);
    }

    public static void main(String[] args) {
        {
            int[] input = new int[] { 1, 1, 1, 1, 1 };
            System.out.println(new TargetSum().findTargetSumWays(input, 3));
        }
        {
            int[] input = new int[] { 0,0,0,0,0,0,0,0,1 };
            System.out.println(new TargetSum().findTargetSumWays(input, 1));
        }
        {
            int[] input = new int[] { 1, 999};
            System.out.println(new TargetSum().findTargetSumWays(input, 998));
        }
    }
}
