package xxx.yyy;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NumberToStringConverter {
    static void convert(char[] array, int pos, Stack<Character> chars) {
        if (pos >= array.length) {
            for (char c : chars) System.out.print(c + " ");
            System.out.println();
            return;
        }

        String arrString0 = String.valueOf(array[pos]);
        Integer val = Integer.valueOf(arrString0);
        char c1 = (char) intToCharMap.get(val).intValue();
        chars.push(c1);
        convert(array, pos+1, chars);
        chars.pop();
        if (pos == array.length-1)
            return;

        char[] arr = new char[] {array[pos], array[pos+1]};
        String arrString = new String(arr);
        Integer val2 = Integer.valueOf(arrString);
        if (intToCharMap.containsKey(val2)) {
            char c2 = (char) intToCharMap.get(val2).intValue();
            chars.push(c2);
            convert(array, pos+2, chars);
            chars.pop();
        }
    }

    static void convert(String s) {
        Stack<Character> chars = new Stack<>();
        convert(s.toCharArray(), 0, chars);
    }
    static final Map<Integer, Integer> intToCharMap = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 1; i <= 26; i++)
            intToCharMap.put(i, 64+i);

        convert("12725");
    }

}
