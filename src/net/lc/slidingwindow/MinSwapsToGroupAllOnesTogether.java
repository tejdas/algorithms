package net.lc.slidingwindow;

/**
 * 1151
 * Sliding Window
 */
public class MinSwapsToGroupAllOnesTogether {
    /**
     * Find number of 1s. Lets's say it is numOnes.
     * Now from left, consider a sliding window of length numOnes.
     * Find the window [l, r] that has the maximum 1s. It means that we need
     * to bring minimum 1s into this window (from outside the window) to keep all
     * 1s together.
     *
     * @param data
     * @return
     */
    public int minSwaps(int[] data) {
        int countOnes = 0;
        int numOnesInWindow = 0;

        int windowEdge = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                countOnes++;

                windowEdge++;
                if (data[windowEdge] == 1) {
                    numOnesInWindow++;
                }
            }
        }

        if (countOnes <= 1) return 0;

        int maxNumOnes = numOnesInWindow;

        for (int i = countOnes; i < data.length; i++) {
            if (data[i-countOnes] == 1) numOnesInWindow--;
            if (data[i] == 1) numOnesInWindow++;

            maxNumOnes = Math.max(maxNumOnes, numOnesInWindow);
        }

        return (countOnes - maxNumOnes);
    }

    public int minSwaps2(int[] data) {
        int countOnes = 0;
        for (int val : data) {
            if (val == 1) countOnes++;
        }

        if (countOnes <= 1) return 0;

        int numOnesInWindow = 0;
        for (int i = 0; i < countOnes; i++) {
            if (data[i] == 1) numOnesInWindow++;
        }

        int maxNumOnes = numOnesInWindow;

        for (int i = countOnes; i < data.length; i++) {
            if (data[i-countOnes] == 1) numOnesInWindow--;
            if (data[i] == 1) numOnesInWindow++;

            maxNumOnes = Math.max(maxNumOnes, numOnesInWindow);
        }

        return (countOnes - maxNumOnes);
    }

    public static void main(String[] args) {
        {
            int[] data = { 1, 0, 1, 0, 1 };
            System.out.println(new MinSwapsToGroupAllOnesTogether().minSwaps(data));
        }
        {
            int[] data = { 1,0,1,0,1,0,0,1,1,0,1 };
            System.out.println(new MinSwapsToGroupAllOnesTogether().minSwaps(data));
        }
        {
            int[] data = {1,0,1,0,1,0,1,1,1,0,1,0,0,1,1,1,0,0,1,1,1,0,1,0,1,1,0,0,0,1,1,1,1,0,0,1};
            System.out.println(new MinSwapsToGroupAllOnesTogether().minSwaps(data));
        }
    }
}
