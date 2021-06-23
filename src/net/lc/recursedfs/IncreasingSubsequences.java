package net.lc.recursedfs;

import java.util.*;

/**
 * 491
 * DFS Recursion
 */
public class IncreasingSubsequences {

    private static final List<Integer> primes = Arrays.asList(3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53);

    private final List<List<Integer>> result = new ArrayList<>();
    Map<Integer, Integer> primeMap = new HashMap<>();
    Set<Integer> uniqueSet = new HashSet<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        System.out.println("----------------");
        if (nums.length == 1) return result;

        int index = 0;
        for (int val : nums) {
            if (!primeMap.containsKey(val)) {
                primeMap.put(val, primes.get(index++));
            }
        }

        for (int i = 0; i < nums.length; i++) {
            Stack<Integer> stack = new Stack<>();
            stack.push(nums[i]);
            subseq(i, nums, stack, primeMap.get(nums[i]));
            stack.pop();
        }

        return result;
    }

    private void subseq(int curIndex, int[] nums, Stack<Integer> stack, int multResult) {
        if (curIndex >= nums.length) return;

        for (int i = curIndex+1; i < nums.length; i++) {
            if (nums[i] >= nums[curIndex]) {
                stack.push(nums[i]);

                int res = multResult * primeMap.get(nums[i]);
                if (!uniqueSet.contains(res)) {
                    List<Integer> l = new ArrayList<>(stack);
                    System.out.println(Arrays.toString(l.toArray()));
                    result.add(l);
                    uniqueSet.add(res);
                }

                subseq(i, nums, stack, res);
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        {
            int[] nums = { 4, 6, 9, 3, 4 };
            new IncreasingSubsequences().findSubsequences(nums);
        }

        {
            int[] nums = {4,6,7,7 };
            new IncreasingSubsequences().findSubsequences(nums);
        }

        {
            int[] nums = {4,4,3,2,1 };
            new IncreasingSubsequences().findSubsequences(nums);
        }

        {
            int[] nums = {1,3,5,7 };
            new IncreasingSubsequences().findSubsequences(nums);
        }
    }
}
