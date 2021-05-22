package net.lc;

import java.util.*;

public class IntegerToRoman {
    private static final Map<Integer, List<Character>> map = new HashMap<>();

    static {
        map.put(1, Arrays.asList('I', 'V', 'X'));
        map.put(10, Arrays.asList('X', 'L', 'C'));
        map.put(100, Arrays.asList('C', 'D', 'M'));
    }

    public String convertToRoman(int input) {

        if (input > 3999) {
            throw new IllegalArgumentException();
        }

        StringBuilder sb = new StringBuilder();

        if (input >= 1000) {
            int q = input / 1000;
            for (int i = 0; i < q; i++) {
                sb.append('M');
            }

            input = input % 1000;
        }

        for (int i = 2; i >=1; i--) {
            int base = (int) Math.pow(10, i);
            if (input >= base) {
                int q = input / base;
                sb.append(convertPartial(q, base));
                input = input % base;
            }
        }

        if (input >= 1) {
            sb.append(convertPartial(input, 1));
        }

        return sb.toString();
    }

    private String convertPartial(int val, int degree) {
        StringBuilder sb = new StringBuilder();
        List<Character> list = map.get(degree);

        char a = list.get(0);
        char b = list.get(1);
        char c = list.get(2);
        switch (val) {
        case 1:
            sb.append(a);
            break;
        case 2:
            sb.append(a);
            sb.append(a);
            break;
        case 3:
            sb.append(a);
            sb.append(a);
            sb.append(a);
            break;
        case 4:
            sb.append(a);
            sb.append(b);
            break;
        case 5:
            sb.append(b);
            break;
        case 6:
            sb.append(b);
            sb.append(a);
            break;
        case 7:
            sb.append(b);
            sb.append(a);
            sb.append(a);
            break;
        case 8:
            sb.append(b);
            sb.append(a);
            sb.append(a);
            sb.append(a);
            break;
        case 9:
            sb.append(a);
            sb.append(c);
            break;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new IntegerToRoman().convertToRoman(78));
        System.out.println(new IntegerToRoman().convertToRoman(9));
        System.out.println(new IntegerToRoman().convertToRoman(357));
        System.out.println(new IntegerToRoman().convertToRoman(2800));
        System.out.println(new IntegerToRoman().convertToRoman(3854));
        System.out.println(new IntegerToRoman().convertToRoman(3999));
        System.out.println(new IntegerToRoman().convertToRoman(4000));
    }
}
