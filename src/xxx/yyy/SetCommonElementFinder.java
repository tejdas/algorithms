package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SetCommonElementFinder {
    private static Integer[] mergeSortedArrays(final int[] aa, final int[] bb) {
        final List<Integer> output = new ArrayList<>();

        int aaIter = 0;
        int bbIter = 0;
        while ((aaIter < aa.length) && (bbIter < bb.length)) {
            if (aa[aaIter] < bb[bbIter]) {
                output.add(aa[aaIter]);
                aaIter++;
            } else if (bb[bbIter] < aa[aaIter]) {
                output.add(bb[bbIter]);
                bbIter++;
            } else {
                output.add(aa[aaIter]);
                output.add(bb[bbIter]);
                aaIter++;
                bbIter++;
            }
        }
        for (int i = aaIter; i < aa.length; i++) {
            output.add(aa[i]);
        }
        for (int i = bbIter; i < bb.length; i++) {
            output.add(bb[i]);
        }
        return output.toArray(new Integer[output.size()]);
    }

    private static void mergeSortedArraysInPlace(final int[] aa, final int[] bb, final int bbLen) {
        int aaIter = aa.length-1;
        int bbIter = bbLen-1;
        int storeIndex = bb.length-1;
        while ((aaIter > 0) && (bbIter > 0)) {
            if (bb[bbIter] > aa[aaIter]) {
            	bb[storeIndex--] = bb[bbIter];
            	bbIter--;
            } else if (aa[aaIter] > bb[bbIter]) {
            	bb[storeIndex--] = aa[aaIter];
                aaIter--;
            } else {
            	bb[storeIndex--] = aa[aaIter];
                aaIter--;
                bbIter--;
            }
        }

        for (int i = aaIter; i > 0; i--) {
        	bb[storeIndex--] = aa[i];
        }
    }

    /**
     * Return a list of common elements in two sorted arrays
     * @param aa sorted array
     * @param bb sorted array
     * @return
     */
    private static List<Integer> getCommonElements(final int[] aa, final int[] bb) {
        final List<Integer> output = new ArrayList<>();

        int aaIter = 0;
        int bbIter = 0;
        while ((aaIter < aa.length) && (bbIter < bb.length)) {
            if (aa[aaIter] < bb[bbIter]) {
                aaIter++;
            } else if (bb[bbIter] < aa[aaIter]) {
                bbIter++;
            } else {
                output.add(aa[aaIter]);
                aaIter++;
                bbIter++;
            }
        }
        return output;
    }

    /**
     * Return a union of two sorted arrays
     * @param aa sorted array
     * @param bb sorted array
     * @return
     */
    private static List<Integer> getUnionElements(final int[] aa, final int[] bb) {
        final List<Integer> output = new ArrayList<>();

        int aaIter = 0;
        int bbIter = 0;
        while ((aaIter < aa.length) && (bbIter < bb.length)) {
            if (aa[aaIter] < bb[bbIter]) {
                output.add(aa[aaIter]);
                aaIter++;
            } else if (bb[bbIter] < aa[aaIter]) {
                output.add(bb[bbIter]);
                bbIter++;
            } else {
                output.add(aa[aaIter]);
                aaIter++;
                bbIter++;
            }
        }

        while (aaIter < aa.length) {
            output.add(aa[aaIter++]);
        }

        while (bbIter < bb.length) {
            output.add(bb[bbIter++]);
        }
        return output;
    }

    private static class EntryPair {
    	public EntryPair(final int first, final int second) {
			super();
			this.first = first;
			this.second = second;
		}
		public int first;
    	public int second;
    }

    private static List<EntryPair> getEntryPairsThatSumTo(final int[] aa, final int[] bb, final int sum) {
    	final List<EntryPair> output = new ArrayList<>();
        int aaIter = 0;
        int bbIter = bb.length-1;
        while ((aaIter < aa.length) && (bbIter > 0)) {
            if (aa[aaIter] + bb[bbIter] == sum) {
                output.add(new EntryPair(aa[aaIter], bb[bbIter]));
                aaIter++;
                bbIter--;
            } else if (aa[aaIter] + bb[bbIter] < sum) {
                aaIter++;
            } else {
            	bbIter--;
            }
        }
    	return output;
    }

    /**
     * Find median of two (sorted) arrays
     * @param aa
     * @param bb
     * @return
     */
    static int findMedian(final int[] aa, final int[] bb) {

        Arrays.sort(aa);
        Arrays.sort(bb);

        int ia = 0;
        int ib = 0;

        int ja = aa.length-1;
        int jb = bb.length-1;

        int minIter = Integer.MIN_VALUE;
        int maxIter = Integer.MAX_VALUE;

        while (minIter < maxIter) {
            if (aa[ia] <= bb[ib]) {
                minIter = aa[ia++];
            } else {
                minIter = bb[ib++];
            }

            if (minIter >= maxIter) {
                System.out.println(minIter + "  " + maxIter);
                break;
            }

            if (aa[ja] >= bb[jb]) {
                maxIter = aa[ja--];
            } else {
                maxIter = bb[jb--];
            }
            System.out.println(minIter + "  " + maxIter);
        }
        return (minIter+maxIter)/2;
    }

    public static void main1(final String[] args) {
        final Random r = new Random();
        final int[] aa = Util.generateRandomNumbers(r, 50);
        final int[] bb = Util.generateRandomNumbers(r, 60);
        System.out.println("Sorted Lists");
        QuickSorter.qsort(r, aa, 0, aa.length-1);
        Util.printArray(aa);
        QuickSorter.qsort(r, bb, 0, bb.length-1);
        Util.printArray(bb);
        final List<Integer> output = getCommonElements(aa, bb);
        System.out.println("Common elements");
        for (final Integer i : output) {
            System.out.print(i + " " );
        }
        System.out.println();

        System.out.println("Merged list");
        final Integer[] merged = mergeSortedArrays(aa, bb);
        Util.printArray(merged);

        final int sum = 100;
        final List<EntryPair> entryPairs = getEntryPairsThatSumTo(aa, bb, sum);
        System.out.println("Entry pairs that sum to: " + sum);
        for (final EntryPair pair : entryPairs) {
        	System.out.println(pair.first + "  " + pair.second);
        }
    }

    public static void main2(final String[] args) {
        final Random r = new Random();
        final int[] aa = Util.generateRandomNumbers(r, 10);
        final int[] bb = Util.generateRandomNumbers(r, 15);
        System.out.println("Sorted Lists");
        QuickSorter.qsort(r, aa, 0, aa.length-1);
        Util.printArray(aa);
        QuickSorter.qsort(r, bb, 0, bb.length-1);
        Util.printArray(bb);
        final int[] cc = new int[aa.length + bb.length];
        Arrays.fill(cc,  0);
        for (int i = 0; i < bb.length; i++) {
        	cc[i] = bb[i];
        }

        mergeSortedArraysInPlace(aa, cc, bb.length);
        Util.printArray(cc);
    }

    public static void main(final String[] args) {
/*
        final Random r = new Random();
        final int[] aa = Util.generateRandomNumbers(r, 10);
        final int[] bb = Util.generateRandomNumbers(r, 15);
        System.out.println("Sorted Lists");
        QuickSorter.qsort(r, aa, 0, aa.length-1);
        Util.printArray(aa);
        QuickSorter.qsort(r, bb, 0, bb.length-1);
        Util.printArray(bb);
*/
        final int[] aa = {40, 47, 50, 64, 102, 104, 122, 157, 167, 182 };
        final int[] bb = {2, 30, 54, 68, 88, 99, 114, 126, 142, 148, 161, 162, 167, 184, 191};
        final int median = findMedian(aa, bb);
        System.out.println(median);

        System.out.println("Merged list");
        final Integer[] merged = mergeSortedArrays(aa, bb);
        Util.printArray(merged);
        if (merged.length %2 == 0) {
            final int midP = merged.length/2;
            System.out.println((merged[midP] + merged[midP-1]) /2);
        } else {
            final int midP = merged.length/2;
            System.out.println(merged[midP]);
        }
    }

    public static void main4(final String[] args) {
        final Random r = new Random();
        final int[] aa = Util.generateRandomNumbers(r, 15); //50);
        final int[] bb = Util.generateRandomNumbers(r, 20); //60);
        System.out.println("Sorted Lists");
        QuickSorter.qsort(r, aa, 0, aa.length-1);
        Util.printArray(aa);
        QuickSorter.qsort(r, bb, 0, bb.length-1);
        Util.printArray(bb);
        {
            final List<Integer> output = getCommonElements(aa, bb);
            System.out.println("Common elements");
            for (final Integer i : output) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        {
            final List<Integer> output = getUnionElements(aa, bb);
            System.out.println("Union elements");
            for (final Integer i : output) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
