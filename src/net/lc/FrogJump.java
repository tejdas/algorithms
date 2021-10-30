package net.lc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 403
 * Array, DP
 */
public class FrogJump {
    public boolean canCross(int[] stones) {

        if (stones.length == 1) {
            return true;
        }

        if (stones[1] - stones[0] > 1) {
            // first jump is 1, meaning can't reach next stone
            return false;
        }

        // stonePos -> array-index map
        Map<Integer, Integer> posMap = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            posMap.put(stones[i], i);
        }

        /**
         * At each array-index, store the set of numbers that was jumped
         * to reach current index.
         * If set is null, it means the current stone was not reached,
         * and therefore can be ignored.
         */
        Map<Integer, Set<Integer>> map = new HashMap<>();

        int firstStone = stones[0];
        if (posMap.containsKey(firstStone + 1)) {
            int index = posMap.get(firstStone + 1);
            if (index == stones.length - 1)
                return true;

            Set<Integer> s = map.computeIfAbsent(index, k -> new HashSet<>());
            s.add(1);
        }

        for (int i = 1; i < stones.length - 1; i++) {
            if (!map.containsKey(i))
                continue;
            Set<Integer> s = map.get(i);

            int stonePos = stones[i];

            for (int prevJumpValue : s) {

                /**
                 * try prevJumpValue-1, prevJumpValue and prevJumpValue+1 for jump
                 */
                for (int jumpVal = prevJumpValue - 1; jumpVal <= prevJumpValue + 1; jumpVal++) {
                    if (jumpVal <= 0) continue;
                    int nextPos = stonePos + jumpVal;

                    if (!posMap.containsKey(nextPos)) {
                        // no stone in that position
                        continue;
                    }

                    int index = posMap.get(nextPos);
                    if (index == stones.length - 1) {
                        // reached last stone
                        return true;
                    }

                    /**
                     * Add the jump value to the next position's set
                     */
                    Set<Integer> ss = map.computeIfAbsent(index, kk -> new HashSet<>());
                    ss.add(jumpVal);

                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        {
            int[] stones = { 0, 1, 3, 5, 6, 8, 12, 17 };
            System.out.println(new FrogJump().canCross(stones));
        }

        {
            int[] stones = { 0, 1, 2, 3, 4, 8, 9, 11 };
            System.out.println(new FrogJump().canCross(stones));
        }
    }
}
