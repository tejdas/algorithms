package algo.recurse;

public class Permutation {
    private static int count = 0;

    private static void swap(final char[] array, final int pos1, final int pos2) {
        final char temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static void printPermutations(final char[] array, final int pos) {
        if (pos == array.length - 1) {
            for (final char c : array) {
                System.out.print(" " + c);
            }
            System.out.println();
            count++;
            return;
        }

        for (int iter = pos; iter < array.length; iter++) {

            swap(array, pos, iter);
            printPermutations(array, pos + 1);
            swap(array, iter, pos);
        }
    }

    public static void main(final String[] args) {
        final String str = "ABCDE";
        printPermutations(str.toCharArray(), 0);
        System.out.println("count: " + count);
    }
}
