package algo.recurse;

import java.util.Stack;

public class DigitsAddTo {

    //static final int[] choices = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
    static final int[] choices = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    public static void main(String[] args) {
        for (final int choice : choices) {
            Stack<Integer> s = new Stack<>();
            addTo(s, 12);
        }
    }

    private static void addTo(Stack<Integer> s, int remaining) {
        for (final int choice : choices) {
            if (choice == remaining) {
                s.push(choice);
                printStack(s);
                s.pop();
            } else if (remaining < choice) {
                continue;
            } else {
                s.push(choice);
                addTo(s, remaining - choice);
                s.pop();
            }
        }
    }
    static void printStack(Stack<Integer> s) {
        for (int i : s) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }
}
