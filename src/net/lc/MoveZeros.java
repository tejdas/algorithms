package net.lc;

/**
 * 283
 * Two-pointer
 */
public class MoveZeros {
    public void moveZeroes(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int pos = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[pos] = array[i];
                pos++;
            }
        }

        if (pos == 0) return;

        for (int i = pos; i < array.length; i++) {
            array[i] = 0;
        }
    }
}
