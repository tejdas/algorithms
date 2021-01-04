package net.lc;

/**
 * Stack-like algorithm
 */
public class MinimumInsertionsToBalanceParens {
    public int minInsertions(String s) {
        int result = 0;

        char[] array = s.toCharArray();
        int unmatchedLeftParens = 0;

        int index = 0;
        while (index < array.length) {
            char c = array[index++];
            if (c == '(') {
                unmatchedLeftParens++;
                continue;
            }

            if ((index < array.length) && (array[index] == ')')) {
                // ))
                if (unmatchedLeftParens > 0) {
                    unmatchedLeftParens--;
                } else {
                    result++;
                }
                index++;
            } else {
                // ) or )(
                if (unmatchedLeftParens > 0) {
                    unmatchedLeftParens--;
                    result++;
                } else {
                    result += 2;
                }
            }
        }

        result += 2 * unmatchedLeftParens;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new MinimumInsertionsToBalanceParens().minInsertions("(()))"));
        System.out.println(new MinimumInsertionsToBalanceParens().minInsertions("())"));
        System.out.println(new MinimumInsertionsToBalanceParens().minInsertions("))())("));
        System.out.println(new MinimumInsertionsToBalanceParens().minInsertions("(((((("));
        System.out.println(new MinimumInsertionsToBalanceParens().minInsertions(")))))))"));

    }
}
