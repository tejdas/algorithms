package xxx.yyy;

import java.util.Arrays;
import java.util.List;

public class Palindrome {
    static List<Character> specialChars = Arrays.asList(new Character[] {',', ':', ' ', '.', ';'});

    static boolean isAlphabet(final int x) {
    	return (x >= 65 && x <= 90) || (x >=97 && x<=122);
    }
    static boolean isPalindrome(final String s) {
        final char[] array = s.toCharArray();
        int i = 0;
        int j = array.length-1;
        while (i < j) {
            if (specialChars.contains(array[i])) { i++; continue; }
            if (specialChars.contains(array[j])) { j--; continue; }

            int x = array[i];
            int y = array[j];

            if (!isAlphabet(x) || !isAlphabet(y)) {
                System.out.println(array[i] + "  " + array[j]);
            	return false;
            }

            x = Character.toUpperCase(x);
            y = Character.toUpperCase(y);

            System.out.println("i: " + i + " j: " + j + " CharAt i: " + (char) x + " CharAt j: " + (char) y);

            if (x == y) {
                i = i+1;
                j = j-1;
            } else {
                System.out.println("Did not match: " + array[i] + "  " + array[j]);
                return false;
            }
        }
        return true;
    }
    public static void main(final String[] args) {
        //System.out.println(isPalindrome("A man, a plaN, a canal: Panama."));
        System.out.println(isPalindrome("Aryaman"));
    }
}
