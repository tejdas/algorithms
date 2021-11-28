package net.lc.greedy;

/**
 * Greedy
 * 277
 */
class Relation {
    boolean knows(int a, int b) {return true;}
}

public class FindCelebrity extends Relation {
    /**
     * create a 2D matrix and populate the values using Relation.knows() API
     * If i knows j, then matrix[i][j] == 1, else 0.
     * For each column, count all the rows containing 1, and if the sum is n (all entries 1),
     * that is a potential celebrity.
     * Now scan the potential celebrity row, and determine whether he does not know anybody.
     *
     * @param n
     * @return
     */
    public int findCelebrity(int n) {
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) matrix[i][j] = 1;
                if (knows(i, j)) matrix[i][j] = 1;
                else matrix[i][j] = 0;
            }
        }

        int potentialCelebrity = -1;
        for (int j = 0; j < n; j++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if (matrix[i][j] == 0) {
                    break;
                } else {
                    sum++;
                }
            }

            if (sum == n) {
                potentialCelebrity = j;
                break;
            }
        }

        if (potentialCelebrity == -1) return -1;
        for (int j = 0; j < n; j++) {
            if (j != potentialCelebrity && matrix[potentialCelebrity][j] == 1) {
                return -1;
            }
        }
        return potentialCelebrity;
    }
}