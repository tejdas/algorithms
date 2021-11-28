package net.lc.greedy;

/**
 * 135
 * Greedy
 * Got HINT
 * Two pass
 * Left to right
 * Right to left
 */
public class Candy {
    public int candy(int[] ratings) {

        if (ratings.length == 0) return 0;

        /**
         * Satisfy candy requirement from left-scan
         */
        int[] leftDist = new int[ratings.length];
        leftDist[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i-1]) {
                leftDist[i] = 1 + leftDist[i-1];
            } else {
                /**
                 * just give one candy
                 */
                leftDist[i] = 1;
            }
        }

        /**
         * Satisfy candy requirement from right-scan
         */
        int[] rightDist = new int[ratings.length];
        rightDist[ratings.length-1] = 1;
        for (int i = ratings.length-2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1]) {
                rightDist[i] = 1 + rightDist[i+1];
            } else {
                /**
                 * just give one candy
                 */
                rightDist[i] = 1;
            }
        }

        /**
         * Two satisfy both neighbors, give the max candy from both scan direction results.
         */
        int sum = 0;
        for (int i = 0; i < ratings.length; i++) {
            sum += Math.max(leftDist[i], rightDist[i]);
        }

        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new Candy().candy(new int[]{1,0,2}));
        System.out.println(new Candy().candy(new int[]{1,2,2}));
    }
}
