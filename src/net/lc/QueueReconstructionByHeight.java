package net.lc;

import java.util.*;

/**
 * 406
 * Greedy
 */
public class QueueReconstructionByHeight {
    private final Map<Integer, List<Integer>> map = new HashMap<>();
    public int[][] reconstructQueue(int[][] people) {
        if (people.length == 0) return new int[0][0];

        map.clear();

        buildMap(people);
        return compute(people.length);
    }

    private void buildMap(int[][] people) {
        for (int[] person : people) {
            List<Integer> list = map.computeIfAbsent(person[0], k-> new ArrayList<>());
            list.add(person[1]);
        }

        for (List<Integer> list : map.values()) {
            Collections.sort(list, Collections.reverseOrder());
        }
    }

    private int[][] compute(int size) {
        List<Integer> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        int[][] result = new int[size][2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = -1;
            }
        }

        for (int key : keys) {
            List<Integer> posList = map.get(key);

            for (int pos : posList) {
                int posToSkip = -1;
                int insertPos = -1;
                for (int i = 0; i < size; i++) {
                    if (result[i][0] == -1) {
                        posToSkip++;
                    }

                    if (posToSkip == pos) {
                        insertPos = i;
                        break;
                    }
                }

                result[insertPos][0] = key;
                result[insertPos][1] = pos;
            }
        }
        return result;
    }
}
