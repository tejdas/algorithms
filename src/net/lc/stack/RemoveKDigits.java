package net.lc.stack;

import java.util.Stack;

/**
 * 402
 * https://leetcode.com/problems/remove-k-digits/
 * Stack
 * Greedy
 */
public class RemoveKDigits {
    public String removeKdigits(String num, int k) {
        if (k == 0) return num;

        if (k >= num.length()) return "0";

        char[] array = num.toCharArray();

        Stack<Integer> stack = new Stack<>();


        StringBuilder sb = new StringBuilder();
        int index = 0;
        /**
         * Final length of num
         */
        int len = num.length() - k;

        while (index < array.length) {
            char c = array[index++];
            int val = Character.digit(c, 10);

            if (stack.isEmpty()) {
                stack.push(val);
                continue;
            }

            /**
             * Try to pop/replace bigger values in stack
             * with smaller values
             */
            while (!stack.isEmpty() && val < stack.peek()) {

                int tofill = len - stack.size();

                int togo = num.length()-index+1;

                if (togo > tofill)
                    stack.pop();
                else {
                    /**
                     * we need the final result of length len.
                     * If there are not enough numbers to reach
                     * len, then we can't afford to pop from stack
                     * anymore.
                     */
                    break;
                }
            }

            /**
             * Push the (bigger) value for now, with the hope
             * of removing later, if possible.
             * Also, cap the stack size to max (len). Its final
             * content is the result
             */
            if (stack.size() < len) {
                stack.push(val);
            }
        }

        for (int val : stack) {
            sb.append(val);
        }

        String res = sb.toString();
        /**
         * Trim any leading zeros.
         */
        index = 0;
        while (index < res.length() && res.charAt(index) == '0') index++;

        if (index == res.length()) return "0";

        if (index > 0)
            return res.substring(index);
        else
            return res;
    }
}
