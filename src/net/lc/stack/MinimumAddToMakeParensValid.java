package net.lc.stack;

import java.util.Stack;

/**
 * 921
 * Stack
 */
public class MinimumAddToMakeParensValid {
    public int minAddToMakeValid(String input) {
        if (input == null || input.length() == 0) return 0;
        char[] array = input.toCharArray();

        Stack<Character> stack = new Stack<>();

        int count = 0;
        for (char c : array) {
            if (c == '(') {
                stack.push(c);
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    // missing corresponding '('
                    count++;
                }
            }
        }
        // if the stack is not-empty, it would contain '('s for which no matching ')' was found
        return count + stack.size();
    }
}
