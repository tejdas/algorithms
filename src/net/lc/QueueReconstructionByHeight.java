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
    }

    private int[][] compute(int size) {
        List<Integer> keys = new ArrayList<>(map.keySet());
        /**
         * We need to honor the queue, so that, we need to put exactly
         * k people of height EQUAL or GREATER than current person.
         *
         * Pick people in the order of their height (shortest people first)
         * For the same height, process in reverse order, so that we can
         * satisfy the invariant (EQUAL) in the position.
         */
        Collections.sort(keys);

        int[][] result = new int[size][2];
        for (int i = 0; i < size; i++) {
            Arrays.fill(result[i], -1);
        }

        for (int key : keys) {
            List<Integer> posList = map.get(key);

            Collections.sort(posList, Collections.reverseOrder());

            for (int pos : posList) {
                /**
                 * pos means there must be pos number of people >= current person.
                 * So, we need to make room for those people, by skipping pos number of
                 * empty slots (-1)
                 */
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

    public static void main(String[] args) {
        int[][] input = {{7,0}, {4,4}, {7,1}, {5,0},{6,1},{5,2}};
        int[][] output = new QueueReconstructionByHeight().reconstructQueue(input);
        for (int[] o : output) {
            System.out.println(Arrays.toString(o));
        }
    }
}
