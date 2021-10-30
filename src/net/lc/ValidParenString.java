package net.lc;

/**
 * https://leetcode.com/problems/valid-parenthesis-string/submissions/
 * 678
 * Greedy
 */
public class ValidParenString {
    public boolean checkValidString(String s) {
        char[] array = s.toCharArray();

        int numStars = 0;
        int lAhead = 0;
        for (char c : array) {
            if (c == '(') {
                lAhead++;
            } else if (c == ')') {
                if (lAhead == 0) {
                    if (numStars == 0) return false;
                    numStars--;
                } else {
                    lAhead--;
                }
            } else {
                numStars++;
            }
        }

        if (lAhead == 0) return true;

        if (lAhead > numStars) return false;

        /**
         * now, scan from right, with the same logic,
         * treating ) as leading, and ( as trailing
         */
        numStars = 0;
        int rAhead = 0;
        for (int i = array.length-1; i >= 0; i--) {
            char c = array[i];

            if (c == ')') {
                rAhead++;
            } else if (c == '(') {
                if (rAhead == 0) {
                    if (numStars == 0) return false;
                    numStars--;
                } else {
                    rAhead--;
                }
            } else {
                numStars++;
            }
        }

        if (rAhead == 0) return true;

        if (rAhead > numStars) return false;

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new ValidParenString().checkValidString("(*))"));
        System.out.println(new ValidParenString().checkValidString("("));

        System.out.println(new ValidParenString().checkValidString("(((((*(()((((*((**(((()()*)()()()*((((**)())*)*)))))))(())(()))())((*()()(((()((()*(())*(()**)()(())"));

    }
}
