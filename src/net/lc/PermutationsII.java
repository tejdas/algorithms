package net.lc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 47
 */
public class PermutationsII {
    private final List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        result.clear();

        if (nums == null) return null;
        if (nums.length == 0) return result;

        printPermutations(nums, 0);

        return result;
    }


    private void printPermutations(final int[] array, final int pos) {
        if (pos == array.length-1) {
            List<Integer> l = new ArrayList<>();
            for (final int c : array) {
                l.add(c);
            }
            result.add(l);
            return;
        }

        final Set<Integer> seen = new HashSet<>();
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
}
