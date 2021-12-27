package net.lc.stack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/online-stock-span/
 * 901
 * Monotonic Stack
 * range query
 */
public class StockSpanner {
    /**
     * The span of the stock's price today is defined as the maximum number of consecutive days
     * (starting from today and going backwards) for which the price of the stock was less than
     * or equal to today's price.
     */
    static class PosPair {
        int stock;
        int pos;

        public PosPair(int stock, int pos) {
            this.stock = stock;
            this.pos = pos;
        }
    }

    private final Stack<PosPair> posStack = new Stack<>();
    private int curPos = 0;
    public StockSpanner() {
    }

    public int next(int price) {
        int span;
        curPos++;
        while (!posStack.isEmpty() && price >= posStack.peek().stock) {
            posStack.pop();
        }

        if (posStack.isEmpty()) {
            span = curPos;
        }
        else {
            span = curPos - posStack.peek().pos;
        }
        /**
         * Push current element.
         */
        posStack.push(new PosPair(price, curPos));
        return span;
    }

    public static void main(String[] args) {
        StockSpanner ss = new StockSpanner();
        System.out.println(ss.next(100));
        System.out.println(ss.next(80));
        System.out.println(ss.next(60));
        System.out.println(ss.next(70));
        System.out.println(ss.next(60));
        System.out.println(ss.next(75));
        System.out.println(ss.next(85));
    }
}
