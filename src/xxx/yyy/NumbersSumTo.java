package xxx.yyy;

import java.util.Stack;

public class NumbersSumTo {
    public static void generateNumbersThatSumTo(final Stack<Integer> output, final int sum) {
        if (sum == 0) {
            for (final int val : output)
                System.out.print(val + "  ");
            System.out.println();
            return;
        }
        int startVal;
        if (output.isEmpty()) {
            startVal = 1;
        } else {
            startVal = output.peek();
        }
        if (startVal > sum) {
            return;
        }
        for (int i = startVal; i <= sum; i++) {
            output.push(i);
            generateNumbersThatSumTo(output, sum - i);
            output.pop();
        }
    }

    public static void main(final String[] args) {
        final Stack<Integer> output = new Stack<>();
        generateNumbersThatSumTo(output, 5);
    }
}
