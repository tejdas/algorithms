package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class InfixToPostfixExpressionConverter {
    private static final Map<Character, Integer> operatorPreference = new HashMap<>();

    private static final List<Character> operators;
    static {
        operatorPreference.put('+', 1);
        operatorPreference.put('-', 1);
        operatorPreference.put('*', 2);
        operatorPreference.put('/', 2);
        operatorPreference.put('^', 4);


        operators = Arrays.asList('(', ')', '+', '-', '*', '/', '^');
    }

    /**
     * Convert an infix algebraic expression to postfix. Uses a stack and a
     * list. Stack is to temporarily store operators. List is to store the
     * postfix expression.
     *
     * @param infix
     * @return
     */
    private static final Character[] infixToPostfix(final char[] infix) {
        final Stack<Character> operatorStack = new Stack<>();
        final List<Character> postfixList = new ArrayList<>();

        for (final char c : infix) {
            /*
             * skip spaces
             */
            if (c == ' ') {
                continue;
            }
            if (operators.contains(c)) {
                /*
                 * Handle operator
                 */
                if (operatorStack.isEmpty() || (c == '(')) {
                    /*
                     * push '('
                     */
                    operatorStack.push(c);
                } else if (c == ')') {
                    /*
                     * continue popping operators from stack and push into list,
                     * until we find a matching '('
                     */
                    while (true) {
                        final char t = operatorStack.pop();
                        if (t == '(') {
                            break;
                        } else {
                            postfixList.add(t);
                        }
                    }
                } else {
                    /*
                     * For operators other than '(' and ')', handle as follows:
                     * if the operator on top of stack has a higher precedence
                     * than the current operator, pop it from stack, and put in
                     * list. (Meaning the operator on top of stack needs to be
                     * acted upon) The exception to this case is '(' which is
                     * considered as highest precedence, and will be popped out
                     * only when a matching ')' is found in the infix array.
                     * Push the current operator to stack.
                     */
                    final Character top = operatorStack.peek();
                    if (top.charValue() != '(') {
                        if (operatorPreference.get(top) > operatorPreference.get(new Character(c))) {
                            postfixList.add(operatorStack.pop());
                        }
                    }
                    operatorStack.push(c);
                }
            } else {
                /*
                 * Add operands directly to the post-fix list.
                 */
                postfixList.add(c);
            }
        }

        /*
         * After processing the infix array is complete, pop the remaining
         * operators from the stack and add to the postfix list.
         */
        while (!operatorStack.isEmpty()) {
            postfixList.add(operatorStack.pop());
        }
        return postfixList.toArray(new Character[postfixList.size()]);
    }

    static int evaluate(final Character[] postfix, final Map<Character, Integer> variables) {
        final Stack<Integer> evalStack = new Stack<>();
        for (final char c : postfix) {
            if (operators.contains(c)) {
                final int val2 = evalStack.pop();
                final int val1 = evalStack.pop();
                int result = 0;

                switch (c) {
                case '+':
                    result = val1 + val2;
                    break;
                case '-':
                    result = val1 - val2;
                    break;
                case '*':
                    result = val1 * val2;
                    break;
                case '/':
                    result = val1 / val2;
                    break;
                case '^':
                    result = (int) Math.pow(val1, val2);
                    break;
                }
                evalStack.push(result);
            } else {
                final int val = variables.get(c);
                evalStack.push(val);
            }
        }
        return evalStack.pop();
    }

    public static void main1(final String[] args) {
        // char[] infix = "(a+b)*c+d*(f-g)".toCharArray();
        //char[] infix = "a * ((b+c) * (d-e)) - f *(g/(h-l)) + m^n-p*q".toCharArray();
        final char[] infix = "a*b + c*d - e/f^g".toCharArray();
        final Character[] postfix = infixToPostfix(infix);
        for (final char c : postfix)
            System.out.print(c);
        System.out.println();
        final Map<Character, Integer> variables = new HashMap<>();
        variables.put('a', 25);
        variables.put('b', 10);
        variables.put('c', 8);
        variables.put('d', 12);
        variables.put('e', 4);
        variables.put('f', 15);
        variables.put('g', 30);
        variables.put('h', 8);
        variables.put('l', 2);
        variables.put('m', 10);
        variables.put('n', 3);
        variables.put('p', 10);
        variables.put('q', 7);

        System.out.println(evaluate(postfix, variables));
    }

    public static void main2(final String[] args) {
        // char[] infix = "(a+b)*c+d*(f-g)".toCharArray();
        //char[] infix = "a * ((b+c) * (d-e)) - f *(g/(h-l)) + m^n-p*q".toCharArray();
        final char[] infix = "a*b/c".toCharArray();
        final Character[] postfix = infixToPostfix(infix);
        for (final char c : postfix)
            System.out.print(c);
        System.out.println();
        final Map<Character, Integer> variables = new HashMap<>();
        variables.put('a', 25);
        variables.put('b', 10);
        variables.put('c', 5);
        variables.put('d', 12);
        variables.put('e', 4);
        variables.put('f', 15);
        variables.put('g', 30);
        variables.put('h', 8);
        variables.put('l', 2);
        variables.put('m', 10);
        variables.put('n', 3);
        variables.put('p', 10);
        variables.put('q', 7);

        System.out.println(evaluate(postfix, variables));
    }

    public static void main(final String[] args) {
        // char[] infix = "(a+b)*c+d*(f-g)".toCharArray();
        final char[] infix = "a * ((b+c) * (d-e)) - f *(g/(h-l)) + m^n-p*q".toCharArray();
        final Character[] postfix = infixToPostfix(infix);
        for (final char c : postfix)
            System.out.print(c);
        System.out.println();
    }
}
