package algo.recurse;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Combinations {
    static int globalCount = 0;
    public static void printCombinations(final char[] array, final Stack<Character> output, final int start, final int count) {
        if (count == 0) {
            for (final char s : output) System.out.print(s);
            System.out.println();
            globalCount++;
            return;
        }

        for (int i = start; i < array.length; i++) {
            output.push(array[i]);
            printCombinations(array, output, i+1, count-1);
            output.pop();
        }
    }

    static final Map<Character, Integer> countMap = new LinkedHashMap<>();

    public static void printCombinationsWithDuplicates(final char[] input, final int combinationCount) {
        countMap.clear();
        for (final char c : input) {
            if (countMap.containsKey(c)) {
                int count = countMap.get(c);
                count++;
                countMap.put(c, count);
            } else {
                countMap.put(c, 1);
            }
        }

        final Set<Character> keys = countMap.keySet();
        final Character[] array = keys.toArray(new Character[keys.size()]);
        System.out.println(Arrays.toString(array));
        final Stack<Character> output = new Stack<>();

        printCombinationsWithDuplicates(array, output, 0, combinationCount);
    }

    private static void printCombinationsWithDuplicates(final Character[] array, final Stack<Character> output,
            final int start, final int count) {
        if (count == 0) {
            for (final char s : output) System.out.print(s);
            System.out.println();
            globalCount++;
            return;
        }

        for (int i = start; i < array.length; i++) {
            final char c = array[i];
            final int occurances = countMap.get(c);
            for (int occurance = 1; occurance <= occurances && occurance <= count; occurance++) {

                for (int k = 0; k < occurance; k++) {
                    output.push(c);
                }
                printCombinationsWithDuplicates(array, output, i + 1, count - occurance);
                for (int k = 0; k < occurance; k++) {
                    output.pop();
                }
            }
        }
    }

    public static void main(final String[] args) {
        //final String str = "10059369";
        String str = "1234";
        final Stack<Character> output = new Stack<>();
        printCombinations(str.toCharArray(), output, 0, 2);
        // printCombinationsWithDuplicates(str.toCharArray(), 7);
        System.out.println(globalCount);
    }

    public static void main2(final String[] args) {
        final String str = "ABADCBCD";

        printCombinationsWithDuplicates(str.toCharArray(), 3);
        //System.out.println(globalCount);
    }
}
