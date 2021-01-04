package net.lc;

/**
 * 135
 * Greedy
 * Got HINT
 */
public class Candy {
    public int candy(int[] ratings) {

        if (ratings.length == 0) return 0;

        int[] leftDist = new int[ratings.length];
        leftDist[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i-1]) {
                leftDist[i] = 1 + leftDist[i-1];
            } else {
                leftDist[i] = 1;
            }
        }

        int[] rightDist = new int[ratings.length];
        rightDist[ratings.length-1] = 1;
        for (int i = ratings.length-2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1]) {
                rightDist[i] = 1 + rightDist[i+1];
            } else {
                rightDist[i] = 1;
            }
        }

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
