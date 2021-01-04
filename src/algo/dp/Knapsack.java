package algo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Knapsack {
    public static void main(String[] args) {
        List<Pair> input = new ArrayList<>();

        input.add(new Pair(2, 3));
        input.add(new Pair(3, 4));
        input.add(new Pair(4, 5));
        input.add(new Pair(5, 8));
        input.add(new Pair(9, 10));

        knapsack(input, 20);

        for (Entry<Tuple, Integer> entry : benefitMap.entrySet()) {
            System.out.println(entry.getValue() + "  " + entry.getKey().toString());
        }
    }

    static class Pair {
        public Pair(int weight, int benefit) {
            super();
            this.weight = weight;
            this.benefit = benefit;
        }

        int weight;
        int benefit;
    }

    static class PairComparator implements Comparator<Pair> {

        @Override
        public int compare(Pair o1, Pair o2) {
            return Double.compare(o1.weight / o1.benefit, o2.weight / o2.benefit);
        }

    }

    static class Tuple {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + index;
            result = prime * result + weight;
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
            Tuple other = (Tuple) obj;
            if (index != other.index)
                return false;
            if (weight != other.weight)
                return false;
            return true;
        }

        public Tuple(int weight, int index) {
            super();
            this.weight = weight;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Tuple [weight=" + weight + ", index=" + index + "]";
        }
        int weight;
        int index;

    }

    static final Map<Tuple, Integer> benefitMap = new HashMap<>();
    static void knapsack(List<Pair> input, int allowedWeight) {
        Pair[] array = input.toArray(new Pair[input.size()]);

        Arrays.sort(array, 0, array.length, new PairComparator());

        int maxBenefit = maximize(allowedWeight, array, 0);
        System.out.println(maxBenefit);
    }

    static int maximize(int allowedWeight, Pair[] array, int index) {

        Tuple t = new Tuple(allowedWeight, index);
        if (benefitMap.containsKey(t)) {
            return benefitMap.get(t);
        }

        int curWeight = array[index].weight;

        if (index == array.length - 1) {
            if (allowedWeight < curWeight) {
                return 0;
            } else {
                return array[index].benefit;
            }
        }

        int benefitWithCurrent = -1;
        int benefitWithoutCurrent = -1;

        if (allowedWeight > curWeight) {
            benefitWithCurrent = array[index].benefit + maximize(allowedWeight - curWeight, array, index + 1);
        }

        benefitWithoutCurrent = maximize(allowedWeight, array, index + 1);

        int benefit = Math.max(benefitWithCurrent, benefitWithoutCurrent);
        benefitMap.put(t, benefit);
        return benefit;
    }
}
