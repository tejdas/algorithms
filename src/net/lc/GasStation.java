package net.lc;

/**
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
        int[] array = new int[gas.length];

        for (int i = 0; i < gas.length; i++) {
            totalCost += cost[i];
            totalGas += gas[i];

            array[i] = gas[i] - cost[i];
        }

        if (totalCost > totalGas) return -1;

        /**
         * See from which station we can start
         */
        int sIndex = -1;
        int index = 0;

        for (int i = 0; i < 2 * array.length-1; i++) {
            if (array[index] >= 0) {
                if (sIndex == -1) {
                    sIndex = index;

                    if (check(array, sIndex)) return sIndex;
                }
            } else {
                sIndex = -1;
            }

            index = (index + 1) % array.length;
        }

        return -1;
    }

    boolean check(int[] array, int startIndex) {
        int sum = 0;

        int idx = startIndex;
        for (int i = 0; i < array.length; i++) {
            sum += array[idx];
            if (sum < 0) return false;
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
