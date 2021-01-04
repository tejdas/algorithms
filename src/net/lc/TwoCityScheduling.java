package net.lc;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 1029
 * https://leetcode.com/problems/two-city-scheduling/submissions/
 * Greedy
 * PriorityQueue
 */
public class TwoCityScheduling {

    /**
     * PQ to pick up city having the lowest cost
     */
    static class Pair implements Comparable<Pair> {
        int index;
        int cityA;
        int cityB;

        public Pair(int index, int cityA, int cityB) {
            this.index = index;
            this.cityA = cityA;
            this.cityB = cityB;
        }

        public char getCity() {
            return Math.min(cityA, cityB) == cityA ? 'a' : 'b';
        }

        @Override
        public int compareTo(Pair o) {
            int minA = Math.min(this.cityA, o.cityA);
            int minB = Math.min(this.cityB, o.cityB);

            if (minA < minB) {
                return Integer.compare(this.cityA, o.cityA);
            } else {
                return Integer.compare(this.cityB, o.cityB);
            }
        }
    }

    /**
     * PQ to pick the index with smallest difference between high-cost and low-cost cities.
     */
    static class DiffPair implements Comparable<DiffPair> {

        int index;
        int lowCostCity;
        int highCostCity;

        public DiffPair(int index, int lowCostCity, int highCostCity) {
            this.index = index;
            this.lowCostCity = lowCostCity;
            this.highCostCity = highCostCity;
        }

        @Override
        public int compareTo(DiffPair o) {
            int thisDiff = this.highCostCity -this.lowCostCity;

            int oDiff = o.highCostCity -o.lowCostCity;

            return Integer.compare(thisDiff, oDiff);

        }
    }
    public int twoCitySchedCost(int[][] costs) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (int index = 0; index < costs.length; index++) {
            int[] cost = costs[index];
            pq.add(new Pair(index, cost[0], cost[1]));
        }

        int totalCost = 0;
        int cityACount = 0;
        int cityBCount = 0;

        char[] chosen = new char[costs.length];
        Arrays.fill(chosen, 'c');

        /**
         * pick the city that has lowest cost.
         * While comparing [A0, B0] and [A1, B1]
         * pick the pair that has the city with cost
         * less than all the other 3 cities.
         */
        while (!pq.isEmpty()) {
            Pair p = pq.remove();
            char city = p.getCity();
            chosen[p.index] = city;
            if (city == 'a') {
                cityACount++;
                totalCost += p.cityA;
            }
            else {
                cityBCount++;
                totalCost += p.cityB;
            }
        }

        if (cityACount == cityBCount) return totalCost;

        /**
         * Since we need to pick equal number of people from
         * two cities, we need to readjust.
         */
        if (cityACount > cityBCount) {
            int toChange = cityACount - (costs.length / 2);

            totalCost = adjustCost(costs, toChange, totalCost, 'a', chosen);
        } else {
            int toChange = cityBCount - (costs.length / 2);
            totalCost = adjustCost(costs, toChange, totalCost, 'b', chosen);
        }

        return totalCost;
    }

    /**
     * Adjust cost in such a way that the rise in cost (while selecting the other city) is minimized.
     * In other words, select those indices where the other city's higher cost difference is minimized.
     * @param costs
     * @param toChange
     * @param totalCost
     * @param city
     * @param chosen
     * @return
     */
    private int adjustCost(int[][] costs, int toChange, int totalCost, char city, char[] chosen) {

        System.out.println("adjustCost");
        int toChangeCity = (city == 'a')? 1 : 0;

        PriorityQueue<DiffPair> diffQ = new PriorityQueue<>();
        for (int index = 0; index < costs.length; index++) {
            int[] cost = costs[index];
            diffQ.add(new DiffPair(index, cost[1-toChangeCity], cost[toChangeCity]));
        }

        while (!diffQ.isEmpty()) {
            DiffPair p = diffQ.remove();
            if (chosen[p.index] == city) {
                totalCost += (p.highCostCity - p.lowCostCity);

                toChange--;
                if (toChange == 0)
                    break;
            }
        }
        return totalCost;
    }

    public static void main(String[] args) {
/*
        {
            //110
            int[][] costs = { { 10, 20 }, { 30, 200 }, { 400, 50 }, { 30, 20 } };
            System.out.println(new TwoCityScheduling().twoCitySchedCost(costs));
        }

        {
            //1859
            int[][] costs = {{259,770},{448,54},{926,667},{184,139},{840,118},{577,469}};
            System.out.println(new TwoCityScheduling().twoCitySchedCost(costs));
        }

        {
            //3086
            int[][] costs = {{515,563},{451,713},{537,709},{343,819},{855,779},{457,60},{650,359},{631,42}};
            System.out.println(new TwoCityScheduling().twoCitySchedCost(costs));
        }
*/
        {
            //4723
            int[][] costs = {{70,311},{74,927},{732,711},{126,583},{857,118},{97,928},{975,843},{175,221},{284,929},{816,602},{689,863},{721,888}};
            System.out.println(new TwoCityScheduling().twoCitySchedCost(costs));
        }
    }
}
