package net.lc.stack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/online-stock-span/
 * 901
 * Stack
 */
public class StockSpanner {
    /**
     * The span of the stock's price today is defined as the maximum number of consecutive days
     * (starting from today and going backwards) for which the price of the stock was less than
     * or equal to today's price.
     */
    static class Pair {
        int stock;
        int span;

        public Pair(int stock, int span) {
            this.stock = stock;
            this.span = span;
        }
    }

    /**
     * Maintain a Stock element of current price, and the span
     * (range of days  which it was greater)
     */
    private final Stack<Pair> stack = new Stack<>();
    public StockSpanner() {
    }

    public int next(int price) {

        int span = 1;
        /**
         * compute the current span, by popping all lesser stocks the top
         * and adding their spans.
         */
        while (!stack.isEmpty() && price >= stack.peek().stock) {
            Pair popped = stack.pop();
            span += popped.span;
        }

        /**
         * Push current element.
         */
        stack.push(new Pair(price, span));
        return span;
    }
}
