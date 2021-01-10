package net.lc;

/**
 * 312
 * Dynamic Programming
 */
public class BurstBalloons {
    public int maxCoins(int[] nums) {

        int[] array = new int[nums.length+2];

        int n = 1;
        for (int x : nums) {
            if (x > 0) array[n++] = x;
        }
        array[0] = 1;
        array[n++] = 1;

        store = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                store[i][j] = -1;
            }
        }
        return getMaxCoins(array, 0, n-1);
    }

    private int[][] store;

    private int getMaxCoins(int[] array, int left, int right) {
        if (left > right) return 0;
        if (store[left][right] != -1) {
            return store[left][right];
        }

        int result = 0;
        for (int index = left+1; index <= right-1; index++) {
            int leftval = getMaxCoins(array, left, index);
            int rightval = getMaxCoins(array, index, right);

            result = Math.max(result, leftval + rightval + (array[left]*array[index]*array[right]));
        }

        store[left][right] = result;
        return result;
    }
}
