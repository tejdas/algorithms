package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/subsets/
 */
public class Subsets {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length == 0)
            return result;

        double max = Math.pow(2, nums.length);
        for (int i = 0; i < max; i++) {
            int[] array = toBinary(i, nums.length);

            List<Integer> list = new ArrayList<>();
            for (int index = 0; index < array.length; index++) {
                if (array[index] == 1) {
                    list.add(nums[index]);
                }
            }
            result.add(list);
        }
        return result;
    }

    private static int[] toBinary(int num, int k) {
        StringBuilder sb = new StringBuilder();

        int[] array = new int[k];
        int index = array.length - 1;
        while (num > 0) {
            int rem = num % 2;
            array[index--] = rem;
            num = num / 2;
        }

        return array;
    }
}
