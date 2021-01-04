package algo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class IncreasingSubsequenceDriver {

    private static final class Key implements Comparable<Key> {

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Key other = (Key) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        public Key(int x, int y) {
            this.x = x;
            this.y = y;
        }

        final int x;
        final int y;

        @Override
        public int compareTo(Key o) {
            if (this.x == o.x) {
                return Integer.compare(this.y, o.y);
            } else {
                return Integer.compare(this.x, o.x);
            }
        }
    }

    /**
     * Dynamic Programming
     *
     * @param array
     */
    private static void generateIncreasingSubsequence(int[] array) {
        /*
         * Data-structure to store all increasing subsequences within the array
         * subset [leftIndex, rightIndex]. The subset is represented by Key.
         */
        final SortedMap<Key, List<List<Integer>>> output = new TreeMap<>();

        final int n = array.length;

        /*
         * Scan the subsets, starting with size 1, then size 2, then size 3, and
         * so on.
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {

                /*
                 * subset boundaries: [x, y]
                 */
                final int x = j;
                final int y = j + i;

                final List<List<Integer>> listOfList = new ArrayList<>();
                output.put(new Key(x, y), listOfList);

                if (x == y) {
                    /*
                     * subset of size 1
                     */
                    final List<Integer> list = new ArrayList<>();
                    list.add(array[x]);
                    listOfList.add(list);
                } else {
                    final int currentElement = array[y];

                    /*
                     * For subsets of size > 1, build up the increasing
                     * subsequences from previous results.
                     */
                    for (int k = x; k < y; k++) {
                        final List<List<Integer>> prevListOfList = output.get(new Key(x, k));

                        /*
                         * Check whether the current element is larger than the
                         * last element of the lists in previous listOfList, and
                         * if so, then create a new list, and add the current
                         * element.
                         */
                        for (final List<Integer> l : prevListOfList) {
                            final int lastElement = l.get(l.size() - 1);
                            if (currentElement >= lastElement) {
                                final List<Integer> list = new ArrayList<>(l);
                                list.add(currentElement);
                                listOfList.add(list);
                            }
                        }
                    }
                }
            }
        }

        /*
         * Display the output
         */
        for (final Key key : output.keySet()) {
            final List<List<Integer>> list = output.get(key);
            for (final List<Integer> l : list) {
                for (final int val : l) {
                    System.out.print(val + "  ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        final int[] array = { 1, 4, 2, 7, 9, 10, 8, 3 };
        System.out.println("Input array: " + Arrays.toString(array));
        System.out.println("Increasing subsequences:");
        generateIncreasingSubsequence(array);
    }
}
