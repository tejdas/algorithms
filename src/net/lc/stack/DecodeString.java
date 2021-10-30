package net.lc.stack;

import java.util.Stack;

/**
 * 394
 * Stack
 * Postfix
 */
public class DecodeString {
    Stack<Integer> istack = new Stack<>();
    Stack<StringBuilder> sstack = new Stack<>();
    public String decodeString(String s) {
        if (s.isEmpty()) return "";
        char[] array = s.toCharArray();
        istack.clear();
        sstack.clear();

        int i = 0;
        while (i < array.length) {
            char c = array[i];
            if (Character.isDigit(c)) {
                int sum = Character.digit(c, 10);

                while (i+1 < array.length && (Character.isDigit(array[i+1]))) {
                    i++;
                    int num = Character.digit(array[i], 10);
                    sum = sum * 10 + num;
                }
                istack.push(sum);
            } else if (c == '[') {
                sstack.push(new StringBuilder());
            } else if (c == ']') {
                int repeatCount = istack.pop();
                String ss = sstack.pop().toString();
                StringBuilder res = new StringBuilder();
                for (int index = 0; index < repeatCount; index++) {
                    res.append(ss);
                }
                if (sstack.isEmpty()) {
                    sstack.push(res);
                } else {
                    sstack.peek().append(res.toString());
                }
            } else {
                if (sstack.isEmpty()) sstack.add(new StringBuilder());
                sstack.peek().append(c);
            }
            i++;
        }

        return sstack.pop().toString();
    }

    public static void main(String[] args) {
        System.out.println(new DecodeString().decodeString("2[abc]3[cd]ef"));
    }
}
