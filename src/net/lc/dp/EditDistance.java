package net.lc.dp;

import java.util.Arrays;

public class EditDistance {
    public int minDistance(String word1, String word2) {
        if (word1.equals(word2)) return 0;

        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();

        int[][] array = new int[w1.length][w2.length];

        int minDistance = 0;
        array[0][0] = w1[0]==w2[0]? 0 : 1;

        for (int i = 0; i < w1.length; i++) {
            for (int j = 0; j < w2.length; j++) {
                if (i == 0 && j == 0) {
                    array[0][0] = w1[0]==w2[0]? 0 : 1;
                } else if (i == 0) {
                    array[i][j] = j;
                } else if (j == 0) {
                    array[i][j] = i;
                } else {
                    int diff = (w1[i] == w2[j])? 0 : 1;

                    int val1 = array[i - 1][j - 1];
                    int val2 = array[i - 1][j];
                    int val3 = array[i][j - 1];

                    array[i][j] = Math.min(diff+val1, Math.min(1+val2, 1+val3));

                }

                //minDistance = Math.max(minDistance, array[i][j]);
            }
        }
        for (int i = 0; i < w1.length; i++) {
            for (int j = 0; j < w2.length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        return array[w1.length-1][w2.length-1];
    }

    public static void main(String[] args) {
        {
            String word1 = "horse";
            String word2 = "ros";
            System.out.println(new EditDistance().minDistance(word1, word2));
        }
        {
            String word1 = "intention";
            String word2 = "execution";
            System.out.println(new EditDistance().minDistance(word1, word2));
        }
    }
}
