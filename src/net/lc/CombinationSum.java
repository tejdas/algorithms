package net.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 39
 * Recursion
 */
public class CombinationSum {
    private final List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result.clear();
        combSum(candidates, target, new Stack<>(), 0);
        return result;
    }

    private void combSum(int[] candidates, int remaining, Stack<Integer> stack, int index) {
        if (remaining == 0) {
            List<Integer> list = new ArrayList<>();
            for (int s : stack) list.add(s);
            result.add(list);
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] <= remaining) {
                stack.push(candidates[i]);
                combSum(candidates, remaining - candidates[i], stack, i);
                stack.pop();
            }
        }
    }
}
