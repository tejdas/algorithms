package net.lc.greedy;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/minimum-number-of-refueling-stops/submissions/
 * 871
 * Greedy
 * PriorityQueue
 */
public class MinRefuelingStops {

    /**
     * Logic
     *
     * Keep track of stations where we did not fuel
     * (in a PriorityQueue with topFuelAvailable at top)
     *
     * From a Station that we decided to stop and refuel (curStop.
     * Find how far it can go (existingFuel + newFuelAtStation)
     * Let it be called rangeMarker.
     *
     * Shortlist all the subsequent stations within rangeMarker.
     * For each shortlisted station, find how far we can go if we decide
     * to refuel there. Let it be called forwardRange.
     *
     * Also pick up the topFuelAvailable from PQ from the ignored stops,
     * and determine the forwardRange if that were picked (rangeMarker + topFuelAvailable).
     *
     * Put all forwardRange in another PQ and pick the max forwardRange.
     *
     * If maxForwardRange >= target, we have reached target.
     * If maxForwardRange < next station, we can't reach, so return -1.
     *
     * Choose the farthest station that comes within forwardRange.
     * This becomes our curStop.
     *
     * Put all the ignored stations in the first PQ.
     */
    static class FuelPair implements Comparable<FuelPair> {
        int fuel;
        int index;

        public FuelPair(int fuel, int index) {
            this.fuel = fuel;
            this.index = index;
        }

        @Override
        public int compareTo(FuelPair o) {
            return Integer.compare(o.fuel, this.fuel);
        }
    }

    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int stops = 0;
        PriorityQueue<FuelPair> skippedStations = new PriorityQueue<>();

        int curStop = -1;
        int fuelLeft = startFuel;
        int curPos = 0;

        while (curPos + fuelLeft < target) {
            /**
             * See how many fuel-stops can be covered with the current fuelLeft
             */
            int farthestWithCurFuel = curPos + fuelLeft;

            int stopIter = curStop+1;
            while (stopIter < stations.length && stations[stopIter][0] <= farthestWithCurFuel) {
                stopIter++;
            }
            stopIter--;

            if (stopIter == curStop) {
                // couldn't even reach next station.
                // check to see if we can use earlier skipped stops
                if (skippedStations.isEmpty()) return -1;

                FuelPair fp = skippedStations.remove();
                stops++;
                fuelLeft += fp.fuel;
            } else {
                /**
                 * With all the fuelSteps covered (upto stopIter), see which one we should stop
                 * and refuel, that will provide us the maximum range.
                 */
                int maxFuelRange = Integer.MIN_VALUE;
                int potentialStopIndex = -1;

                for (int i = curStop+1; i <= stopIter; i++) {

                    int fuelLeftFromI = fuelLeft - (stations[i][0] - curPos) + stations[i][1];
                    int range = stations[i][0] + fuelLeftFromI;
                    if (range > maxFuelRange) {
                        maxFuelRange = range;
                        potentialStopIndex = i;
                    }
                }

                // could we pick up an earlier skipped stop, that beats the above potential stops?
                if (!skippedStations.isEmpty() && curPos + fuelLeft + skippedStations.peek().fuel > maxFuelRange) {
                    FuelPair fp = skippedStations.remove();
                    stops++;
                    fuelLeft += fp.fuel;
                    continue;
                } else {
                    // put everything before it in skippedStations
                    for (int j = curStop+1; j < potentialStopIndex; j++) {
                        skippedStations.add(new FuelPair(stations[j][1], j));
                    }

                    curStop = potentialStopIndex;
                    stops++;
                    fuelLeft = fuelLeft - (stations[curStop][0] - curPos) + stations[curStop][1];
                    curPos = stations[curStop][0];
                    if (curPos + fuelLeft >= target) return stops;
                }
            }
        }
        return stops;
    }

    public static void main(String[] args) {

        {
            // 2
            int[][] stations = { { 10, 60 }, { 20, 30 }, { 30, 30 }, { 60, 40 } };
            System.out.println(new MinRefuelingStops().minRefuelStops(100, 10, stations));
        }

        {
            // -1
            int[][] stations = { { 10, 100 } };
            System.out.println(new MinRefuelingStops().minRefuelStops(100, 1, stations));
        }
        {
            // 0
            int[][] stations = { };
            System.out.println(new MinRefuelingStops().minRefuelStops(1, 1, stations));
        }

        {
            // 4
            int[][] stations = { {13,21},{26,115},{100,47},{225,99},{299,141},{444,198},{608,190},{636,157},{647,255},{841,123} };
            System.out.println(new MinRefuelingStops().minRefuelStops(1000, 299, stations));
        }

        {
            // 7 (correct answer 5)
            int[][] stations = {{1793,82439},{52411,214982},{84252,253430},{94323,218186},{114953,24666},{283259,6913},{686905,127560},{745682,302336},{756487,154828},{845106,87596}};
            System.out.println(new MinRefuelingStops().minRefuelStops(1000000, 9440, stations));
        }

    }
}
