package net.lc.bfs;

import java.util.*;

/**
 * 773
 * BFS
 */
public class SlidingPuzzle {
    private final int[] array = new int[] {0, 1, 2, 3, 4, 5};
    private static final String DEST = "123450";
    private final Map<String, Set<String>> adjMap = new HashMap<>();

    private final Map<String, Integer> nodesDistance = new HashMap<>();

    public int slidingPuzzle(int[][] board) {
        int[] start = new int[array.length];
        int pos = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                start[pos++] = board[i][j];
            }
        }

        String startNode = convertToString(start);

        if (adjMap.isEmpty()) {
            perm(0);
        }
        return bfs(startNode);
    }

    private void perm(int curIndex) {
        if (curIndex == array.length-1) {
            neighbors();
            return;
        }

        for (int i = curIndex; i < array.length; i++) {
            swap(i, curIndex);
            perm(curIndex+1);
            swap(i, curIndex);
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void neighbors() {
        String key = convertToString();
        Set<String> vals = new HashSet<>();
        int zeroPos = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                zeroPos = i;
                break;
            }
        }

        if ((zeroPos%3 != 0) && zeroPos-1 >= 0) {
            swap(zeroPos, zeroPos-1);
            vals.add(convertToString());
            swap(zeroPos, zeroPos-1);
        }

        if (zeroPos-3 >= 0) {
            swap(zeroPos, zeroPos-3);
            vals.add(convertToString());
            swap(zeroPos, zeroPos-3);
        }

        if (((zeroPos+1) %3 != 0) && zeroPos+1 < array.length) {
            swap(zeroPos, zeroPos+1);
            vals.add(convertToString());
            swap(zeroPos, zeroPos+1);
        }

        if (zeroPos+3 < array.length) {
            swap(zeroPos, zeroPos+3);
            vals.add(convertToString());
            swap(zeroPos, zeroPos+3);
        }

        vals.forEach(val -> nodesDistance.put(val, Integer.MAX_VALUE));

        adjMap.put(key, vals);
    }

    private String convertToString() {
        return convertToString(array);
    }

    private String convertToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int val : array) {
            sb.append(Character.forDigit(val, 10));
        }
        return sb.toString();
    }

    private int bfs(String startPos) {
        if (startPos.equals(DEST)) return 0;
        nodesDistance.put(startPos, 0);

        Queue<String> queue = new LinkedList<>();

        queue.add(startPos);
        while (!queue.isEmpty()) {
            String curNode = queue.remove();

            int curDistance = nodesDistance.get(curNode);
            for (String adj : adjMap.get(curNode)) {

                if (nodesDistance.get(adj) > 1 + curDistance) {
                    nodesDistance.put(adj, 1 + curDistance);

                    if (adj.equals(DEST)) {
                        return nodesDistance.get(adj);
                    }

                    queue.add(adj);
                }
            }

        }
        return -1;
    }
}
