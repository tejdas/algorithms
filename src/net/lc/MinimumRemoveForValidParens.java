package net.lc;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/submissions/
 * 1249
 * Stack
 */
public class MinimumRemoveForValidParens {

    private final Set<Integer> toRemove = new HashSet<>();

    public String minRemoveToMakeValid(String s) {
        char[] array = s.toCharArray();

        Stack<Integer> posStack = new Stack<>();
        for (int index = 0; index < array.length; index++) {
            char c = array[index];

            if (c == '(') {
                posStack.push(index);
            } else if (c == ')') {
                if (posStack.isEmpty()) {
                    /**
                     * no matching '(', therefore must be removed
                     */
                    toRemove.add(index);
                } else {
                    /**
                     * found a matching ( for a ). So, we are good
                     */
                    posStack.pop();
                }
            }
        }

        /**
         * Remaining in the stack are '('s for which we did not find matching ')'
         * These must be removed.
         */
        while (!posStack.isEmpty()) {
            toRemove.add(posStack.pop());
        }

        if (toRemove.isEmpty()) return s;
        char[] output = new char[array.length - toRemove.size()];

        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (!toRemove.contains(i)) {
                output[index++] = array[i];
            }
        }
        return String.valueOf(output);
    }

    public static void main(String[] args) {
        String s = "lee(t(c)o)de)";
        System.out.println(new MinimumRemoveForValidParens().minRemoveToMakeValid(s));

        System.out.println(new MinimumRemoveForValidParens().minRemoveToMakeValid("a)b(c)d"));

        System.out.println(new MinimumRemoveForValidParens().minRemoveToMakeValid("))(("));

        System.out.println(new MinimumRemoveForValidParens().minRemoveToMakeValid("(a(b(c)d)"));

    }
}
