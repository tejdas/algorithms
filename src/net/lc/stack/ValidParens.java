package net.lc.stack;

import java.util.*;

/**
 * https://leetcode.com/problems/valid-parentheses/
 * 20
 * Stack
 */
public class ValidParens {
    static Set<Character> beginChars = new HashSet<>(Arrays.asList('{', '(', '['));

    public boolean isValid(String s) {

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        if (s == null || s.isEmpty()) return true;

        Stack<Character> stack = new Stack<>();

        char[] array = s.toCharArray();

        for (char c : array) {
            if (beginChars.contains(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;

                char topchar = stack.pop();
                if (map.get(c) != topchar) return false;
            }
        }

        return stack.isEmpty();
    }
}
