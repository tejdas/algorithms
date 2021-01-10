package net.lc;

import java.util.Stack;

/**
 * 735
 * Stack
 */
public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null || asteroids.length == 0) return null;

        Stack<Integer> s = new Stack<>();
        for (int val : asteroids) {
            if (s.isEmpty()) {
                s.push(val);
                continue;
            }

            if (val > 0) {
                /**
                 * moving right; so add to stack
                 */
                s.push(val);
                continue;
            }

            boolean consumed = false;
            while (val < 0 && !s.isEmpty()) {
                /**
                 * moving left
                 */

                if (s.peek() < 0) {
                    /**
                     * will never meet
                     */
                    break;
                }

                if (Math.abs(val) == s.peek()) {
                    /**
                     * both explode
                     */
                    consumed = true;
                    s.pop();
                    break;
                } else if (Math.abs(val) < s.peek()) {
                    /**
                     * new value is smaller and hence consumed
                     */
                    consumed = true;
                    break;
                } else {
                    /**
                     * consume the top of the stack and continue
                     */
                    s.pop();
                }
            }

            if (!consumed) {
                s.push(val);
            }
        }

        int[] res = new int[s.size()];
        int index = 0;
        for (int val : s) {
            res[index++] = val;
        }
        return res;
    }
}
