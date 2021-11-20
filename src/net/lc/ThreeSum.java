package net.lc;

import java.util.*;

/**
 * 15
 */
public class ThreeSum {
    private final Set<List<Integer>> result = new HashSet<>();
    public List<List<Integer>> threeSum(int[] nums) {
        result.clear();
        Arrays.sort(nums);
        generateSetsOfThreeNumbersThatSumToZero(nums);

        return new ArrayList<>(result);
    }

    /**
     * Call the above method for every element of the array
     * Time complexity : O(N^2)
     *
     * Plus, since we need a sorted array, time complexity of sorting the array
     * with QuickSort is O(NLogN)
     *
     * @param array: Sorted array
     */
    private void generateSetsOfThreeNumbersThatSumToZero(final int[] array) {
        for (int i = 0; i < array.length; i++) {
            getEntryPairsThatSumTo(array, i);
        }
    }

    private void getEntryPairsThatSumTo(final int[] array, final int index) {
    	/*
    	 * Negate array[index] because, we want the sum to be 0
    	 */
        final int sum = -array[index];

    	/*
    	 * beginIter goes from begin to end
    	 * endIter goes from end to begin
    	 */
        int beginIter = 0;
        int endIter = array.length-1;

        while (beginIter < endIter) {
            if (beginIter == index) {
                // skip index
                beginIter++;
                continue;
            }
            if (endIter == index) {
                // skip index
                endIter--;
                continue;
            }

            if (array[beginIter] + array[endIter] == sum) {
                List<Integer> list = new ArrayList<>();
                list.add(-sum);
                list.add(array[beginIter]);
                list.add(array[endIter]);
                Collections.sort(list);
                result.add(list);
                beginIter++;
                endIter--;
            } else if (array[beginIter] + array[endIter] < sum) {
                beginIter++;
            } else {
                endIter--;
            }
        }
    }
}
