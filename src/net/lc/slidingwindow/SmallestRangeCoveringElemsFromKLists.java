package net.lc.slidingwindow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/submissions/
 * 632
 * Sliding-windows
 * Greedy
 */
public class SmallestRangeCoveringElemsFromKLists {
    static class Pair implements Comparable<Pair> {
        int val;
        int listIndex;

        public Pair(int val, int listIndex) {
            this.val = val;
            this.listIndex = listIndex;
        }

        @Override
        public int compareTo(Pair o) {
            if (this.val == o.val) {
                return Integer.compare(this.listIndex, o.listIndex);
            }
            return Integer.compare(this.val, o.val);
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        int count = nums.size();

        if (count == 1) {
            int v = nums.get(0).get(0);
            return new int[] {v, v};
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        for (int i = 0; i < count; i++) {
            List<Integer> list = nums.get(i);
            for (int val : list) {
                pq.add(new Pair(val, i));
            }
        }

        int[] seen = new int[count];
        int countSeen = 0;

        int minRange = Integer.MAX_VALUE;
        int left = Integer.MIN_VALUE;
        int right = -1;

        int minLeft = Integer.MIN_VALUE;
        int minRight = -1;

        LinkedList<Pair> ll = new LinkedList<>();

        while (!pq.isEmpty()) {
            Pair p = pq.remove();

            if (left == Integer.MIN_VALUE) {
                left = p.val;
            }

            if (seen[p.listIndex] == 0) {
                seen[p.listIndex]++;
                countSeen++;
                ll.addLast(p);

                if (countSeen == count) {
                    right = p.val;

                    if ((right - left + 1) < minRange) {
                        minRange = right-left+1;
                        minLeft = left;
                        minRight = right;
                    }
                } else {
                    // haven't seen all lists yet
                }
            } else {
                // have seen this list before
                ll.addLast(p);
                seen[p.listIndex]++;
                while (!ll.isEmpty()) {
                    Pair head = ll.peekFirst();
                    if (seen[head.listIndex] == 1) break;
                    ll.removeFirst();
                    seen[head.listIndex]--;
                    left = ll.peekFirst().val;
                }

                if (countSeen == count) {
                    right = ll.peekLast().val;
                    if ((right - left + 1) < minRange) {
                        minRange = right-left+1;
                        minLeft = left;
                        minRight = right;
                    }
                }
            }
        }
        return new int[] {minLeft, minRight};
    }

    public static void main(String[] args) {
        {
            List<Integer> l1 = Arrays.asList(1,4,7,10,13);
            List<Integer> l2 = Arrays.asList(2,5,8,11,13);
            List<Integer> l3 = Arrays.asList(3,6,9,12);

            List<List<Integer>> input = Arrays.asList(l1, l2, l3);
            int[] res = new SmallestRangeCoveringElemsFromKLists().smallestRange(input);
            System.out.println(Arrays.toString(res));
        }

        {
            List<Integer> l1 = Arrays.asList(4, 10, 15, 24, 26);
            List<Integer> l2 = Arrays.asList(0, 9, 12, 20);
            List<Integer> l3 = Arrays.asList(5, 18, 22, 30);

            List<List<Integer>> input = Arrays.asList(l1, l2, l3);
            int[] res = new SmallestRangeCoveringElemsFromKLists().smallestRange(input);
            System.out.println(Arrays.toString(res));
        }

        {
            List<Integer> l1 = Arrays.asList(1,2,3);
            List<Integer> l2 = Arrays.asList(1,2,3);
            List<Integer> l3 = Arrays.asList(1,2,3);

            List<List<Integer>> input = Arrays.asList(l1, l2, l3);
            int[] res = new SmallestRangeCoveringElemsFromKLists().smallestRange(input);
            System.out.println(Arrays.toString(res));
        }
        {
            List<Integer> l1 = Arrays.asList(10,10);
            List<Integer> l2 = Arrays.asList(11,11);

            List<List<Integer>> input = Arrays.asList(l1, l2);
            int[] res = new SmallestRangeCoveringElemsFromKLists().smallestRange(input);
            System.out.println(Arrays.toString(res));
        }

        {
            List<List<Integer>> input = Arrays.asList(Arrays.asList(1), Arrays.asList(2), Arrays.asList(3), Arrays.asList(4),
                Arrays.asList(5), Arrays.asList(6), Arrays.asList(7));
            int[] res = new SmallestRangeCoveringElemsFromKLists().smallestRange(input);
            System.out.println(Arrays.toString(res));
        }

    }

}
