package net.lc;

import java.util.HashMap;
import java.util.Map;

public class CombinationSumIV {
    private final Map<Integer, Integer> map = new HashMap<>();
    public int combinationSum4(int[] array, int target) {
        if (array == null || target == 0) {
            return 0;
        }

        map.clear();
        return combinationSum(array, target);
    }

    private int combinationSum(int[] array, final int remainingSteps) {

        if (remainingSteps == 0) {
            return 1;
        }

        if (map.containsKey(remainingSteps)) {
            return map.get(remainingSteps);
        }

        int totalSteps = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > remainingSteps) {
                continue;
            }

            totalSteps += combinationSum(array, remainingSteps - array[i]);
        }
        map.put(remainingSteps, totalSteps);
        return totalSteps;
    }
}
