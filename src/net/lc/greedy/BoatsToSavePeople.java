package net.lc.greedy;

import java.util.Arrays;

/**
 * Greedy
 * Sorting
 * Two-pointers
 * 881
 */
public class BoatsToSavePeople {
    /**
     * Pick heaviest and lightest person each time.
     * If can't fit both, just pick the heaviest person.
     * @param people
     * @param limit
     * @return
     */
    public int numRescueBoats(int[] people, int limit) {
        if (people == null || people.length == 0) return 0;
        Arrays.sort(people);

        int numBoats = 0;

        int i = 0;
        int j = people.length-1;

        while (i < j) {
            if (people[i] + people[j] <= limit) {
                numBoats++;
                i++;
                j--;
            } else {
                numBoats++;
                j--;
            }
        }
        if (i == j) numBoats++;
        return numBoats;
    }
}
