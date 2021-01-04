package net.lc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/monotone-increasing-digits/submissions/
 * Stack
 */
public class MonotoneIncreasingDigits {
    public int monotoneIncreasingDigits(int N) {
        Integer[] array = toArray(N);

        boolean monotone = true;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i-1]) {
                monotone = false;
                break;
            }
        }

        if (monotone) return N;

        int size = array.length;

        Stack<Integer> stack = new Stack<>();
        int index = 0;
        while (index < array.length) {
            int val = array[index];
            if (stack.isEmpty() || val >= stack.peek().intValue()) {
                stack.push(val);
                index++;
                continue;
            }

            while (!stack.isEmpty() && val < stack.peek()) {
                val = stack.pop().intValue() - 1;
                index--;
            }

            if (val > 0) {
                stack.push(val);
            }
            index++;
            break;
        }

     //   for (int i = index; i < size; i++) {
       //     stack.push(9);
        //}

        int sum = 0;
        for (int v : stack) {
            sum = sum*10 + v;
        }

        for (int i = index; i < size; i++) {
            sum = sum * 10 + 9;
        }
        return sum;
    }

    private Integer[] toArray(int N) {
        List<Integer> l = new ArrayList<>();
        while (N > 0) {
            int rem = N % 10;
            l.add(rem);
            N = N / 10;
        }

        Collections.reverse(l);

        return l.toArray(new Integer[l.size()]);
    }

    public static void main(String[] args) {
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(43572));
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(2662));
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(26774));
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(110));
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(100));
        System.out.println(new MonotoneIncreasingDigits().monotoneIncreasingDigits(4321));
    }
}
