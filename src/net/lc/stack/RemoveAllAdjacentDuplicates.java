package net.lc.stack;

import java.util.Stack;

/**
 * 1209
 * Stack
 */
public class RemoveAllAdjacentDuplicates {
    static class Elem {
        char c;
        int count;

        public Elem(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    /**
     * Remove k-consecutive duplicate chars.
     * Keep count of chars and repeat-count on stack.
     *
     * @param s
     * @param k
     * @return
     */
    public String removeDuplicates(String s, int k) {
        char[] array = s.toCharArray();

        Stack<Elem> stack = new Stack<>();

        for (char c : array) {
            if (!stack.isEmpty() && c == stack.peek().c) {
                stack.peek().count++;

                if (stack.peek().count == k) {
                    stack.pop();
                }
            } else {
                stack.push(new Elem(c, 1));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Elem elem : stack) {
            for (int i = 0; i < elem.count; i++) {
                sb.append(elem.c);
            }
        }
        return sb.toString();
    }
}
