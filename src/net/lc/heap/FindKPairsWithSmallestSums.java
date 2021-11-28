package net.lc.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 373
 * Heap
 * Greedy
 */
public class FindKPairsWithSmallestSums {
    static class PQEntry implements Comparable<PQEntry> {
        final int val1;
        final int val2;
        int index1;
        int index2;

        public PQEntry(int val1, int val2) {
            this.val1 = val1;
            this.val2 = val2;
            //System.out.println(val1 + ": " + val2);
        }

        public PQEntry(int val1, int val2, int index1, int index2) {
            this.val1 = val1;
            this.val2 = val2;
            this.index1 = index1;
            this.index2 = index2;
        }

        @Override
        public int compareTo(PQEntry o) {
            return Integer.compare(this.val1+this.val2, o.val1+o.val2);
        }
    }

    private List<List<Integer>> output = new ArrayList<>();
    private int[] array1;
    private int[] array2;
    private int len1;
    private int len2;
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        output.clear();

        if (nums1.length == 0 || nums2.length == 0) return output;

        k = Math.min(k, nums1.length * nums2.length);

        array1 = nums1;
        array2 = nums2;
        len1 = array1.length;
        len2 = array2.length;
        process(k);
        return output;
    }

    /**
     * First add upto k numbers of array1, paired with array2[0] to PriorityQueue
     * Then, as you remove and process the head of the PQ (smallest pair) of {index1, index2}
     * add {index1, index2+1} to PQ. In other words, replace the head of the PQ with the current index of array1
     * and next index of array2.
     *
     * @param k
     */
    private void process(int k) {
        PriorityQueue<PQEntry> pq = new PriorityQueue<>();
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < k; i++) {
            //System.out.println("adding: " + index1 + "   :  " + index2);
            pq.add(new PQEntry(array1[index1], array2[index2], index1, index2));
            index1++;
            if (index1 == len1) {
                break;
            }
        }

        for (int i = 0; i < k; i++) {
            PQEntry entry = pq.remove();
            output.add(Arrays.asList(entry.val1, entry.val2));
            if (output.size() == k) break;

            index1 = entry.index1;
            index2 = entry.index2;
            //System.out.println("from PQ: removed: " + index1 + "   :  " + index2);
            index2++;
            if (index2 == len2) {
                continue;
            }
            //System.out.println(" PQ: adding: " + index1 + "   :  " + index2);
            pq.add(new PQEntry(array1[index1], array2[index2], index1, index2));
        }
    }
}
