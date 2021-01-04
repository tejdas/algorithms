package algo.recurse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.ArrayUtils;

public class Multipliers {

    public static void main(final String[] args) {
        final int number = 96;
        final Integer[] array = findFactors(number);
        Arrays.sort(array);
        ArrayUtils.reverse(array);
        System.out.print("Factors ===> ");
        for (final int val : array) {
            System.out.print(val + "  ");
        }
        System.out.println();

        final Stack<Integer> stack = new Stack<>();
        multipliers(array, 0, number, stack);
    }

    static Integer[] findFactors(final int number) {
        final Set<Integer> factors = new HashSet<>();
        int val = 2;
        while ((val * val) <= number) {
            if (number % val == 0) {
                factors.add(val);
                factors.add(number/val);
            }
            val++;
        }

        return factors.toArray(new Integer[factors.size()]);
    }

    static void multipliers(final Integer[] factors, final int index, final int product, final Stack<Integer> multiples) {
        if (product == 1) {
            for (final int factor : multiples) System.out.print(factor + "  ");
            System.out.println();
            return;
        }

        for (int i = index; i < factors.length; i++) {
            final int val = factors[i];
            if (product % val == 0) {
                multiples.push(val);
                multipliers(factors, i, product/val, multiples);
                multiples.pop();
            }
        }
    }
}
