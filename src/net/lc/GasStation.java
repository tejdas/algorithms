package net.lc;

/**
 * 134
 * https://leetcode.com/problems/gas-station/
 * Greedy
 */
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int totalGas = 0;
        int totalCost = 0;

        /**
         * keep track of the delta at each station:
         * gas available - gas spent to go to next station
         */
        int[] delta = new int[gas.length];

        for (int i = 0; i < gas.length; i++) {
            totalCost += cost[i];
            totalGas += gas[i];

            delta[i] = gas[i] - cost[i];
        }

        if (totalCost > totalGas) return -1;

        /**
         * See from which station we can start
         */
        int sIndex = -1;
        int index = 0;

        /**
         * We must start from a Gas station with a positive delta,
         * i.e, you leave the station with more Gas than before.
         *
         * If there are a series of stations with -ve delta, followed
         * by a series of stations with +ve delta, start from the first
         * +ve station after a series of -ve stations. Meaning, keep accumulating
         * at the start of the trip, and then close the trip with mostly spending.
         *
         * In other words, always start from a +ve station that is preceded by a
         * -ve station.
         */
        for (int i = 0; i < 2 * delta.length-1; i++) {
            if (delta[index] >= 0) {
                if (sIndex == -1) {
                    sIndex = index;

                    if (check(delta, sIndex)) return sIndex;
                } else {
                    /**
                     * Do not start from a +ve station, which is preceded by a +ve station.
                     */
                }
            } else {
                /**
                 * we encountered a -ve station, so reset sIndex to -1.
                 */
                sIndex = -1;
            }

            index = (index + 1) % delta.length;
        }

        return -1;
    }

    boolean check(int[] array, int startIndex) {
        int sum = 0;

        int idx = startIndex;
        for (int i = 0; i < array.length; i++) {
            sum += array[idx];
            if (sum < 0) {
                // we cannot complete the trip by starting at startIndex,
                // because we just ran out of gas
                return false;
            }
            idx = (idx + 1) % array.length;
        }
        return true;
    }

    public static void main(String[] args) {
        {
            int[] gas = { 1, 2, 3, 4, 5 };
            int[] cost = { 3, 4, 5, 1, 2 };

            System.out.println(new GasStation().canCompleteCircuit(gas, cost));
        }

        {
            int[] gas = {2,3,4};
            int[] cost = { 3, 4, 3 };

            System.out.println(new GasStation().canCompleteCircuit(gas, cost));
        }

        {
            int[] gas = { 1, 2, 3, 4, 5 };
            int[] cost = { 3, 4, 5, 1, 2 };

            System.out.println(new GasStation().canCompleteCircuit(gas, cost));
        }
    }
}
