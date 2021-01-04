package algo.recurse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Permutations {
    private static int count = 0;
    private static void swap(final char[] array, final int pos1, final int pos2) {
        final char temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static void printPermutations(final char[] array, final int pos) {
        if (pos == array.length-1) {
            for (final char c : array) {
                System.out.print(" " + c);
            }
            System.out.println();
            count++;
            return;
        }

        final Set<Character> seen = new HashSet<>();
        for (int iter = pos; iter < array.length; iter++) {

            if (seen.contains(array[iter])) {
                continue;
            }

            seen.add(array[iter]);

            swap(array, pos, iter);
            printPermutations(array, pos+1);
            swap(array, iter, pos);
        }
    }

    static void swap(final int[] a, final int posx, final int posy) {
        final int temp = a[posx];
        a[posx] = a[posy];
        a[posy] = temp;
    }

    static int printDerangements(final int n) {
        final Map<Integer, List<int[]>> map = new HashMap<>();

        for (int i = 2; i <= n; i++) {
            final List<int[]> list = new ArrayList<>();
            if (i == 2) {
                list.add(new int[] {2, 1});
            } else {
                final List<int[]> prevList = map.get(i-1);

                for (final int[] a : prevList) {
                    final int[] aa = new int[a.length+1];
                    System.arraycopy(a, 0, aa, 0, a.length);
                    aa[aa.length-1] = i;

                    for (int j = 0; j < a.length; j++) {
                        swap(aa, j, aa.length-1);

                        final int[] aaa = new int[aa.length];
                        System.arraycopy(aa, 0, aaa, 0, aa.length);

                        list.add(aaa);
                        swap(aa, j, aa.length-1);
                    }
                }
            }
            map.put(i, list);
        }

        final List<int[]> finalList = map.get(n);

        for (final int[] array : finalList) {
            System.out.println(Arrays.toString(array));
        }
        return finalList.size();
    }

    public static void main(final String[] args) {
        final String str = "AAAAABBBBB";
        //String str = "ABCDEFG";
        printPermutations(str.toCharArray(), 0);
        System.out.println("count: " + count);
    }

    public static void main2(final String[] args) {
        final int size = printDerangements(8);
        System.out.println("size: " + size);
    }
}
