package algo.recurse;

import java.util.Stack;

public class ClimbStepsGenerator {
    static int[] allowedSteps = {3, 2, 1};
    //static int[] allowedSteps = {512, 64, 8, 1};

    public static void waysToClimbSteps(final int steps) {
        final Stack<Integer> stepsSofar = new Stack<Integer>();
        climbSteps(steps, 0, stepsSofar);
    }

    private static void climbSteps(final int remainingSteps, final int index, final Stack<Integer> stepsSofar) {

        if (remainingSteps == 0) {
            for (final int val : stepsSofar) System.out.print(val + "  ");
            System.out.println();
            return;
        }

        for (int i = index; i < allowedSteps.length; i++) {
            if (allowedSteps[i] > remainingSteps) {
                continue;
            }

            stepsSofar.push(allowedSteps[i]);

            climbSteps(remainingSteps - allowedSteps[i], i, stepsSofar);
            stepsSofar.pop();
        }
    }

    static int count = 0;
    private static void climbStepsMostEfficiently(final int remainingSteps, final int index, final Stack<Integer> stepsSofar) {

        if (count == 1) return;
        if (remainingSteps == 0) {
            for (final int val : stepsSofar) System.out.print(val + "  ");
            System.out.println();
            count++;
            return;
        }

        for (int i = index; i < allowedSteps.length; i++) {
            if (allowedSteps[i] > remainingSteps) {
                continue;
            }

            stepsSofar.push(allowedSteps[i]);

            climbStepsMostEfficiently(remainingSteps - allowedSteps[i], i, stepsSofar);
            stepsSofar.pop();
        }
    }


    public static void main(final String[] args) {
        waysToClimbSteps(15);
    }
}
