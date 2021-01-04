package xxx.yyy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PhoneNumberMappings {
    static final Map<Character, List<Character>> map = new HashMap<>();
    static {
        map.put('1',  Arrays.asList('1'));
        map.put('0',  Arrays.asList('0'));
        map.put('2',  Arrays.asList('A', 'B', 'C'));
        map.put('3',  Arrays.asList('D', 'E', 'F'));
        map.put('4',  Arrays.asList('G', 'H', 'I'));
        map.put('5',  Arrays.asList('J', 'K', 'L'));
        map.put('6',  Arrays.asList('M', 'N', 'O'));
        map.put('7',  Arrays.asList('P', 'Q', 'R', 'S'));
        map.put('8',  Arrays.asList('T', 'U', 'V'));
        map.put('9',  Arrays.asList('W', 'X', 'Y', 'Z'));

    }

    static class Info {

        public Info(final int pos, final int choicePos) {
            super();
            this.pos = pos;
            this.choicePos = choicePos;
        }

        int pos;
        int choicePos = 0;
    }

    static int count = 0;
    static void displayMappings(final String phoneNumber) {
        final char[] input = phoneNumber.toCharArray();
        final Stack<Info> stack = new Stack<>();

        stack.push(new Info(0, -1));

        while (!stack.isEmpty()) {

            final Info top = stack.peek();
            top.choicePos++;
            final int pos = top.pos;

            final List<Character> list = map.get(input[pos]);
            if (top.choicePos == list.size()) {
                stack.pop();
                continue;
            }

            if (top.pos == input.length-1) {
                for (final Info info : stack) {
                    final List<Character> l = map.get(input[info.pos]);
                    System.out.print(l.get(info.choicePos));
                }
                System.out.println();
                count++;
            } else {
                stack.push(new Info(top.pos+1, -1));
            }
        }
    }

    static void displayPhoneMappingsRecurse(final String phoneNumber) {
        final char[] input = phoneNumber.toCharArray();
        final char[] output = new char[input.length];
        displayPhoneMappingsRecurse(input, 0, output);
    }

    static void displayPhoneMappingsRecurse(final char[] input, final int pos, final char[] output) {
        if (pos == input.length) {
            for (final char c : output) System.out.print(c);
            System.out.println();
            count++;
            return;
        }

        final List<Character> list = map.get(input[pos]);
        for (final char c : list) {
            output[pos] = c;
            displayPhoneMappingsRecurse(input, pos+1, output);
        }
    }

    public static void main(final String[] args) {
        final String input = "5082752725";
        displayMappings(input);
        System.out.println(count);
    }
}
