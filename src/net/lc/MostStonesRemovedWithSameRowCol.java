package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
 */
public class MostStonesRemovedWithSameRowCol {
    private int[] parentArray;
    private final Map<Integer, Set<Integer>> xMap = new HashMap<>();
    private final Map<Integer, Set<Integer>> yMap = new HashMap<>();
    private final Map<Integer, Integer> unionsetSizeMap = new HashMap<>();

    public int removeStones(int[][] stones) {
        if (stones == null || stones.length == 0) return 0;

        xMap.clear();
        yMap.clear();
        unionsetSizeMap.clear();
        parentArray = new int[stones.length];
        Arrays.fill(parentArray, -1);

        for (int index = 0; index < stones.length; index++) {
            int[] stone = stones[index];
            int x = stone[0];
            int y = stone[1];

            parentArray[index] = index;
            unionsetSizeMap.put(index, 1);

            int parx = -1;
            int pary = -1;

            if (!xMap.containsKey(x)) {
                xMap.put(x, new HashSet<>());
                xMap.get(x).add(index);
            } else {
                Set<Integer> set = xMap.get(x);
                parx = set.iterator().next();
                set.add(index);
                union(index, parx);
            }

            if (!yMap.containsKey(y)) {
                yMap.put(y, new HashSet<>());
                yMap.get(y).add(index);
            } else {
                Set<Integer> set = yMap.get(y);
                pary = set.iterator().next();
                set.add(index);
                union(index, pary);
            }

            if (parx != -1 && pary != -1) {
                union(pary, parx);
            }
        }
        int sum = 0;
        for (int val : unionsetSizeMap.values()) {
            if (val > 1) {
                sum += (val - 1);
            }
        }
        return sum;
    }

    private int findParent(int x) {
        if (x != parentArray[x]) {
            int par = findParent(parentArray[x]);
            parentArray[x] = par;
        }
        return parentArray[x];
    }

    private void union(int x, int y) {
        int parx = findParent(x);
        int pary = findParent(y);

        if (parx != pary) {
            int sizex = unionsetSizeMap.get(parx);
            int sizey = unionsetSizeMap.get(pary);

            if (sizex >= sizey) {
                parentArray[pary] = parx;
                unionsetSizeMap.remove(pary);
                unionsetSizeMap.put(parx, sizex+sizey);
            } else {
                parentArray[parx] = pary;
                unionsetSizeMap.remove(parx);
                unionsetSizeMap.put(pary, sizex+sizey);
            }
        }
    }
}
