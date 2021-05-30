package net.lc.recursedfs;

import java.util.*;

/**
 * 17
 * DFS
 * Recursion
 */
public class LettterCombinationPhoneNumber {
    private final List<String> result = new ArrayList<>();
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

    void displayPhoneMappingsRecurse(final String phoneNumber) {
        final char[] input = phoneNumber.toCharArray();
        final char[] output = new char[input.length];
        displayPhoneMappingsRecurse(input, 0, output);
    }

    void displayPhoneMappingsRecurse(final char[] input, final int pos, final char[] output) {
        if (pos == input.length) {
            result.add(new String(output));
            return;
        }

        final List<Character> list = map.get(input[pos]);
        for (final char c : list) {
            output[pos] = Character.toLowerCase(c);
            displayPhoneMappingsRecurse(input, pos+1, output);
        }
    }

    public List<String> letterCombinations(String digits) {
        result.clear();

        if (digits == null || digits.isEmpty()) {
            return result;
        }
        displayPhoneMappingsRecurse(digits);
        return result;
    }
}
