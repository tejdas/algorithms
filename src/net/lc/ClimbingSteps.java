package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 70
 * Recursion
 * Dynamic Programming
 */
public class ClimbingSteps {
    static int[] allowedSteps = {1, 2};

    private Map<Integer, Integer> map = new HashMap<>();
    private int climbSteps(final int remainingSteps) {

        if (remainingSteps == 0) {
            return 1;
        }

        if (map.containsKey(remainingSteps)) {
            return map.get(remainingSteps);
        }

        int totalSteps = 0;
        for (int i = 0; i < allowedSteps.length; i++) {
            if (allowedSteps[i] > remainingSteps) {
                continue;
            }

            totalSteps += climbSteps(remainingSteps - allowedSteps[i]);
        }

        map.put(remainingSteps, totalSteps);
        return totalSteps;
    }

    public int climbStairs(int steps) {
        return climbSteps(steps);
    }
}
