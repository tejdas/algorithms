package xxx.yyy;

import java.util.Stack;

/**
 * @author tdas
 *
 */
public class JumpingNumbers {

    static int gCount = 0;

    public static void main(final String[] args) {
        printJumpingNumbers(5);
        System.out.println(gCount);
    }

    static void printJumpingNumbers(final int n) {
        for (int i = 0; i <= 9; i++) {
            final Stack<Integer> stack = new Stack<>();
            stack.push(i);
            printJumpingNumbers(n, 1, stack);
            stack.pop();
        }
    }
    static void printJumpingNumbers(final int n, final int count, final Stack<Integer> stack) {
        if (count == n) {
            for (final int i : stack) System.out.print(i);
            System.out.println();
            gCount++;
            return;
        }

        final int val = stack.peek();
        if (val > 0 && val < 9) {
            stack.push(val-1);
            printJumpingNumbers(n, count+1, stack);
            stack.pop();

            stack.push(val+1);
            printJumpingNumbers(n, count+1, stack);
            stack.pop();
        } else if (val == 0) {
            stack.push(val+1);
            printJumpingNumbers(n, count+1, stack);
            stack.pop();
        } else if (val == 9) {
            stack.push(val-1);
            printJumpingNumbers(n, count+1, stack);
            stack.pop();
        } else {
            return;
        }
    }
}
