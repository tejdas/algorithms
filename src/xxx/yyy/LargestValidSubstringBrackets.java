package xxx.yyy;

public class LargestValidSubstringBrackets {

    public static void main(final String[] args) {
        System.out.println(findLargestValidSubstringBrackets("((()"));
        System.out.println(findLargestValidSubstringBrackets(")()())"));
        System.out.println(findLargestValidSubstringBrackets(")()())()(()))))"));
    }

    static int findLargestValidSubstringBrackets(final String input) {
        final char[] array = input.toCharArray();

        int largestLength = 0;

        int currentLength = 0;
        int braceDepth = 0;

        for (final char c : array) {
            if (c == '(') {
                braceDepth++;
            } else if (c == ')') {
                if (braceDepth > 0) {
                    braceDepth--;
                    currentLength += 2;
                    if (currentLength > largestLength) {
                        largestLength = currentLength;
                    }
                } else {
                    currentLength = 0;
                    braceDepth = 0;
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
        return largestLength;
    }
}
