package net.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 42
 * https://leetcode.com/problems/trapping-rain-water/submissions/
 * Stack
 * We need to find higher elevations that can trap rainwater.
 * We can ignore all the lower elevations in-between two higher elevations.
 *
 * Use a stack to keep-track of higher-elevations.
 * Bottom of the stack is a higher elevation.
 * (a) If a new elevation is smaller than stack peek, push it (lower elevation).
 * (b) Otherwise, keep popping until we find a higher-elevation, and then push the current elevation.
 * If stack becomes empty during (b), then the last stack element is an elevation.
 *
 * When all the elevations are processed, the remaining stack elements (in a non-decreasing height) become
 * all chosen elevations.
 *
 * Iterate on all elevations, and compute rain-water trapped (exclude the height of the intermediate submerged
 * points).
 */
public class TrappingRainWater {
    static class Pair {
        int height;
        int index;

        public Pair(int height, int index) {
            this.height = height;
            this.index = index;
        }
    }

    public int trap(int[] array) {

        Stack<Pair> stack = new Stack<>();
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0)
                continue;

            Pair p = new Pair(array[i], i);
            if (stack.isEmpty()) {
                stack.push(p);
                indexList.add(i);
                continue;
            }

            if (p.height < stack.peek().height) {
                stack.push(p);
            } else {
                Pair lastPopped = null;

                while (!stack.isEmpty() && p.height >= stack.peek().height) {
                    lastPopped = stack.pop();
                }

                if (stack.isEmpty()) {
                    if (lastPopped != null) {
                        indexList.add(lastPopped.index);
                    }
                }
                stack.push(p);
            }
        }


        if (!stack.isEmpty()) {
            Stack<Integer> revStack = new Stack<>();
            while (!stack.isEmpty()) {
                revStack.push(stack.pop().index);
            }

            while (!revStack.isEmpty()) {
                indexList.add(revStack.pop());
            }
        }

        int sum = 0;

        for (int i = 1; i < indexList.size(); i++) {
            int cur = indexList.get(i);
            int prev = indexList.get(i-1);

            if (cur-prev > 1) {
                int height = Math.min(array[cur], array[prev]);
                sum += height * (cur-prev-1);
                for (int j = cur-1; j > prev; j--) {
                    sum -= array[j];
                }
            }
        }

        return sum;
    }
}
