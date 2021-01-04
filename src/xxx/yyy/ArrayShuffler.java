package xxx.yyy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ArrayShuffler {

    static void shuffle(final int[] array, final int[] pos) {
        validatePositionArray(array, pos);

        final int[] arrayClone = new int[array.length];
        System.arraycopy(array, 0, arrayClone, 0, array.length);

        for (int i = 0; i < array.length; i++) {

            if (array[pos[i]] == arrayClone[i]) {
                continue;
            }

            final Stack<Integer> stack = new Stack<>();
            int startPos = i;
            stack.push(startPos);

            int curPos = pos[startPos];

            while (curPos != i) {
                stack.push(curPos);
                startPos = curPos;
                curPos = pos[startPos];
            }

            for (final int val : stack) System.out.print(val + "   ");
            System.out.println();

            int prevVal = stack.pop();
            final int stored = array[prevVal];

            while (!stack.isEmpty()) {
                final int curVal = stack.pop();

                array[prevVal] = array[curVal];

                prevVal = curVal;
            }

            array[prevVal] = stored;
            System.out.println(Arrays.toString(array));
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * @param array
     * @param pos
     */
    private static void validatePositionArray(final int[] array, final int[] pos) {
        if (array == null || pos == null) {
            throw new IllegalArgumentException();
        }

        if (array.length != pos.length) {
            throw new IllegalArgumentException();
        }

        final Set<Integer> posSet = new HashSet<>();
        for (final int val : pos) {
            if (val < 0 || val > array.length-1) {
                throw new IllegalArgumentException();
            }
            posSet.add(val);
        }

        if (posSet.size() != pos.length) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(final String[] args) {
        final int[] array = {4, 3, 10, 8, 5};

        final int[] pos = {4, 3, 2, 1, 0};

        shuffle(array, pos);
    }
}
