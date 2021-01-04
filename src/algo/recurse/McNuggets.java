package algo.recurse;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class McNuggets {
    static final int[] choices = {20, 9, 6};
    static boolean canBuy(final int remaining, final Stack<Integer> order) {
        for (final int choice : choices) {
            if (choice == remaining) {
                order.push(choice);
                return true;
            } else if (remaining < choice) {
                continue;
            } else {
                order.push(choice);
                if (!canBuy(remaining-choice, order)) {
                    order.pop();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    static int numberOfWays(final int num) {
        final Map<Integer, Integer> countMap = new HashMap<>();
        return numberOfWays(num, countMap);
    }

    static int numberOfWays(final int num, final Map<Integer, Integer> countMap) {

        if (countMap.containsKey(num)) {
            return countMap.get(num);
        }

        int count = 0;

        try {
            if (num < 6) {
                count = 0;
            } else if (num == 6) {
                count = 1;
            } else if (num == 9) {
                count = 1;
            } else if (num == 20) {
                count = 1;
            } else {
                for (final int choice : choices) {
                    if (num > choice) {
                        count += numberOfWays(num - choice, countMap);
                    }
                }
            }
            return count;
        } finally {
            if (num >= 0) {
                countMap.put(num, count);
            }
        }
    }

    public static void main0(final String[] args) {
        int largestNumberNotBought = 0;
        for (int i = 50; i < 51; i++) {
            final Stack<Integer> order = new Stack<>();
            if (canBuy(i, order)) {
                System.out.print("Can buy: " + i + "  ");
                for (final int o : order)
                    System.out.print(o + " ");
                System.out.println();
            } else {
                System.out.println("Cannot buy: " + i);
                if (i > largestNumberNotBought) {
                    largestNumberNotBought = i;
                }
            }
        }

        System.out.println("largest number not bought: " + largestNumberNotBought);
    }

    public static void main(final String[] args) {
        final int count = numberOfWays(15);
        System.out.println(count);
    }
}
