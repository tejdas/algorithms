package xxx.yyy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;


public class Controller {

    public static void main(final String[] args) {
        validateInput("5 4 3 7 1", 3);

        try (final Scanner s = new Scanner(System.in)) {
            final int numLines = s.nextInt();
            if (numLines <= 0) {
                return;
            }

            int curLine = 0;

            while (s.hasNext()) {
                final String line = s.nextLine();
                curLine++;
                if (curLine < numLines) {
                    validateInput(line, curLine);
                } else {
                    System.out.println(String.format("WRONG INPUT (LINE %d)", curLine));
                }
            }
        }
    }

    static void validateInput(final String input, final int line) {
        try {
            final String[] vals = input.split(" ");
            if (vals == null || vals.length == 0) {
                return;
            }

            /*
             * Determine duplicate eventId
             */
            final boolean[] flag = new boolean[vals.length];
            Arrays.fill(flag, false);

            int maxValue = 1;

            for (final String val : vals) {
                try {
                    final int eventId = Integer.valueOf(val);

                    if (eventId < 1) {
                        System.out.println(String.format("WRONG INPUT (LINE %d)", line));
                        return;
                    }
                    if (eventId > maxValue) {
                        maxValue = eventId;

                        if (maxValue > vals.length) {
                            System.out.println("FAILURE: RECEIVED " + vals.length + " EXPECTED: " + maxValue);
                            return;
                        }
                    }

                    if (flag[eventId - 1]) {
                        /*
                         * Duplicate Event Id
                         */
                        System.out.println(String.format("WRONG INPUT (LINE %d)", line));
                        return;
                    }

                    flag[eventId - 1] = true;

                } catch (final NumberFormatException ex) {
                    System.out.println(String.format("WRONG INPUT (LINE %d)", line));
                    return;
                }
            }

            if (vals.length != maxValue) {
                System.out.println("FAILURE: RECEIVED " + vals.length + " EXPECTED: " + maxValue);
            } else {
                System.out.println("SUCCESS: RECEIVED " + vals.length);
            }

        } catch (final PatternSyntaxException ex) {
            System.out.println(String.format("WRONG INPUT (LINE %d)", line));
        }
    }
}
