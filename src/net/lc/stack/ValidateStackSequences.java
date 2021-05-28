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
    public boolean validateStackSequences2(int[] pushed, int[] popped) {

        Stack<Integer> stack = new Stack<>();

        int pushIndex = 0;
        int popIndex = 0;

        while (pushIndex < pushed.length) {

            if (pushed[pushIndex] == popped[popIndex]) {
                pushIndex++;
                popIndex++;
                if (popIndex == popped.length) break;
            }


            while (!stack.isEmpty() && stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
                if (popIndex == popped.length) break;
            }

            if (pushIndex == pushed.length) break;
            stack.push(pushed[pushIndex++]);
        }

        while (!stack.isEmpty() && popIndex < popped.length) {
            if (popped[popIndex] != stack.pop()) return false;
            popIndex++;
        }

        if (stack.isEmpty() && popIndex == popped.length) return true;
        return false;
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {

        Stack<Integer> stack = new Stack<>();

        int pushIndex = 0;
        int popIndex = 0;

        Set<Integer> seen = new HashSet<>();

        while (popIndex < popped.length) {
            int popVal = popped[popIndex];

            while (!seen.contains(popVal) && pushIndex < pushed.length) {
                int pushval = pushed[pushIndex++];
                stack.push(pushval);
                seen.add(pushval);
            }

            while (seen.contains(popVal) && !stack.isEmpty()) {
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
