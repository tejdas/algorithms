package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 986
 * The other way we could solve is to directly compare Intervals (rather than edges)
 * Pick first intervals from both array and compare for overlap.
 *
 * Whichever list's interval ends first, pick the next one from the list, and so on.
 * Stop once we reach end of any single list.
 */
public class IntervalListIntersections {
    private static class IEdge implements Comparable<IEdge> {
        int val;
        int edge; // 0 start, 1 end

        public IEdge(int val, int edge) {
            this.val = val;
            this.edge = edge;
        }

        @Override
        public int compareTo(IEdge o) {
            if (this.val == o.val) {
                return Integer.compare(this.edge, o.edge);
            }

            return Integer.compare(this.val, o.val);
        }
    }

    public int[][] intervalIntersection(int[][] A, int[][] B) {

        List<IEdge> list = new ArrayList<>();

        for (int[] interval : A) {
            list.add(new IEdge(interval[0], 0));
            list.add(new IEdge(interval[1], 1));
        }

        for (int[] interval : B) {
            list.add(new IEdge(interval[0], 0));
            list.add(new IEdge(interval[1], 1));
        }


        IEdge[] listArray = list.toArray(new IEdge[list.size()]);
        Arrays.sort(listArray);

        List<int[]> output = new ArrayList<>();

        int overlapped = 0;
        int lastBegin = -1;
        for (IEdge val : listArray) {
            if (val.edge == 0) {
                // begin
                if (overlapped > 0) {
                    lastBegin = val.val;
                }
                overlapped++;
            } else {
                overlapped--;
                if (overlapped > 0) {
                    output.add(new int[] {lastBegin, val.val});
                }
            }
        }
        int[][] resultArray = new int[output.size()][];
        int index = 0;
        for (int[] val : output) {
            resultArray[index++] = val;
        }
        return resultArray;
    }
}
