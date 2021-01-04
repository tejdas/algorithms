package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SmallestSubarrayContainingSet {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>(Arrays.asList(3, 5, 9));

        int[] array = new int[] { 7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7 };

        findSubArray(set, array);

    }

    static void findSubArray(Set<Integer> set, int[] array) {

        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int val : set) {
            map.put(val, new ArrayList<>());
        }

        for (int index = 0; index < array.length; index++) {
            if (set.contains(array[index])) {
                map.get(array[index]).add(index);
            }
        }

        Map<Integer, Integer[]> indexMap = new HashMap<>();

        for (Entry<Integer, List<Integer>> entry : map.entrySet()) {
            indexMap.put(entry.getKey(), entry.getValue().toArray(new Integer[entry.getValue().size()]));
        }

        Iterator<Integer> iter = indexMap.keySet().iterator();
        Integer firstKey = iter.next();
        Integer[] firstArray = indexMap.remove(firstKey);

        int minDeviation = Integer.MAX_VALUE;
        int minDeviationIndex = -1;
        for (int val : firstArray) {
            int sum = 0;
            for (Integer[] arr : indexMap.values()) {
                int nearest = findNearest(arr, 0, arr.length - 1, val);
                int dev = Math.abs(val - nearest);
                sum += dev;
            }

            if (sum < minDeviation) {
                minDeviation = sum;
                minDeviationIndex = val;
            }
        }

        int[] output = new int[set.size()];
        output[0] = minDeviationIndex;

        int index = 1;
        for (Integer[] arr : indexMap.values()) {
            int nearest = findNearest(arr, 0, arr.length - 1, minDeviationIndex);
            output[index++] = nearest;
        }

        Arrays.sort(output);

        int min = output[0];
        int max = output[output.length - 1];

        for (int i = min; i <= max; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println();
    }

    static int findNearest(final Integer[] array, final int start, final int end, final int val) {
        if (array.length == 0)
            return -1;
        if (val <= array[start])
            return array[start];
        if (val >= array[end])
            return array[end];
        if (end == start + 1) {
            if ((val - array[start]) < (array[end] - val))
                return array[start];
            else
                return array[end];
        }
        final int median = (start + end) / 2;
        if (val == array[median])
            return val;
        else if (val < array[median])
            return findNearest(array, start, median, val);
        else {
            return findNearest(array, median, end, val);
        }
    }
}
