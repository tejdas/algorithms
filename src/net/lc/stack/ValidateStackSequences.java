package net.lc.stack;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * https://leetcode.com/problems/validate-stack-sequences/submissions/
 * Stack solution
 * 946
 */
public class ValidateStackSequences {
    public boolean validateStackSequences(int[] pushed, int[] popped) {

        Stack<Integer> stack = new Stack<>();

        int pushIndex = 0;
        int popIndex = 0;

        /**
         * Use this to keep track of values seen in the pushed array
         */
        boolean[] seen = new boolean[1001];

        while (popIndex < popped.length) {
            int popVal = popped[popIndex];

            /**
             * push the values from pushed array into the stack until we have seen a value from popped array
             */
            while (!seen[popVal] && pushIndex < pushed.length) {
                int pushval = pushed[pushIndex++];
                stack.push(pushval);
                seen[pushval] = true;
            }

            /**
             * Now try to pop the values from stack as long as they match with popped array
             * and are seen before.
             */
            while (seen[popVal] && !stack.isEmpty()) {
                if (stack.peek() != popVal) return false;
                popIndex++;
                stack.pop();
                if (popIndex == popped.length) break;
                popVal = popped[popIndex];

            }
        }

        return (stack.isEmpty() && popIndex == popped.length);
    }

    public static void main(String[] args) {
        {
            int[] pushed = { 1, 2, 3, 4, 5 };
            int[] popped = { 4, 5, 3, 2, 1 };

            System.out.println(new ValidateStackSequences().validateStackSequences(pushed, popped));
        }

        {
            int[] pushed = { 1, 2, 3, 4, 5 };
            int[] popped = { 4, 3,5,1,2 };

            System.out.println(new ValidateStackSequences().validateStackSequences(pushed, popped));
        }
    }
}
