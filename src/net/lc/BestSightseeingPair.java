package net.lc;

public class BestSightseeingPair {
    static class Pair {
        int i;
        int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
    public int maxScoreSightseeingPair2(int[] values) {
        Pair[] memo = new Pair[values.length];

        memo[0] = new Pair(0,0);
        memo[1] = new Pair(0, 1);

        for (int i = 2; i < values.length; i++) {

            Pair prev = memo[i-1];

            int curMax = values[prev.j] + values[prev.i] - (prev.j - prev.i);

            int m1 = values[prev.j] + values[i] - (i - prev.j);

            int m2 = values[prev.i] + values[i] - (i - prev.i);

            if (curMax > Math.max(m1, m2)) {
                memo[i] = prev;
            } else {
                if (m1 > m2) {
                    memo[i] = new Pair(prev.j, i);
                } else {
                    memo[i] = new Pair(prev.i, i);
                }
            }
        }

        Pair last = memo[values.length-1];
        int maxValue = values[last.j] + values[last.i] - (last.j - last.i);
        return maxValue;
    }

    public int maxScoreSightseeingPair(int[] values) {

        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;

        int maxIter = -1, minIter = -1;

        for (int i = 0; i < values.length; i++) {
            if (i + values[i] > maxSum) {
                maxSum = i + values[i];
                maxIter = i;
            }

            if ((i - values[i]) < minSum) {
                minSum = i - values[i];
                minIter = i;
            }
        }
        System.out.println("maxIter: " + maxIter + "    minIter: " + minIter);
        return values[minIter] + values[maxIter] + (minIter-maxIter);
    }

    public static void main(String[] args) {
        System.out.println(new BestSightseeingPair().maxScoreSightseeingPair(new int[] {8,1,5,2,6}));
    }
}
