package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/buildings-with-an-ocean-view/
 * 1762
 * Greedy
 */
public class BuildingsWithOceanView {
    public int[] findBuildings(int[] heights) {

        if (heights == null || heights.length == 0) {
            return new int[]{};
        }

        List<Integer> output = new ArrayList<>();
        int hIndex = heights.length-1;

        int curTallest = heights[hIndex];
        output.add(hIndex);

        for (int i = hIndex-1; i >= 0; i--) {
            if (heights[i] > curTallest) {
                curTallest = heights[i];
                output.add(i);
            }
        }


        int[] oparray = new int[output.size()];
        int index = oparray.length-1;
        for (int val : output) {
            oparray[index--] = val;
        }
        return oparray;
    }

    public static void main(String[] args) {
        int[] op = new BuildingsWithOceanView().findBuildings(new int[]{4,2,3,1});
        System.out.println(Arrays.toString(op));
    }
}
