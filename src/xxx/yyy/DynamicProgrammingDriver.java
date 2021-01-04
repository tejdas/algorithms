package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicProgrammingDriver {
    static void longestIncreasingSubsequence(final int[] array) {
        final Map<Integer, List<Integer>> sequenceMap = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            int longest = Integer.MIN_VALUE;
            int longestIndex = -1;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[i]) {
                    continue;
                }

                final List<Integer> seq = sequenceMap.get(j);
                if (seq == null) {
                    continue;
                }

                if (seq.size() > longest) {
                    longest = seq.size();
                    longestIndex = j;
                }
            }

            final List<Integer> seq = new ArrayList<>();
            if (longestIndex != -1) {
                seq.addAll(sequenceMap.get(longestIndex));
            }
            seq.add(array[i]);
            sequenceMap.put(i, seq);
        }

        for (int i = 0; i < array.length; i++) {
            final List<Integer> longestSeq = sequenceMap.get(i);
            Util.printArray(longestSeq.toArray(new Integer[longestSeq.size()]));
        }
    }

    static void coinChanger(final int money) {
        final int[] coins = {2, 3, 5, 6};

        final int[][] combinations = new int[money+1][coins.length];

        for (int i = 0; i < coins.length; i++) {
            combinations[0][i] = 1;
        }

        for (int amt=1; amt <= money; amt++) {
            for (int j = 0; j < coins.length; j++) {
                int x = 0;
                if (j > 0) {
                    x = combinations[amt][j-1];
                }

                int y = 0;
                if (amt >= coins[j]) {
                    y = combinations[amt-coins[j]][j];
                }

                combinations[amt][j] = x + y;
            }
        }

        MatrixRotator.printMatrix(combinations, money+1, coins.length);
        //System.out.println(combinations[money][coins.length-1]);
    }

    public static void main1(final String[] args) {
        coinChanger(1); // 622 55 532 3322 22222
        // 663 6522 63222 6333 555 53322 522222 33333 333222 3222222
    }

    public static void main(final String[] args) {
        final int[] array = new int[] {5, 2, 4, 3, 10, 7, 8, 15, 12, 6, 19, 18, 28};
        longestIncreasingSubsequence(array);
    }
}
