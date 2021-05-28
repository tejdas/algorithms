package net.lc.stack;

import java.util.*;

public class BasicCalculatorII {
    private static final Map<Character, Integer> operatorPreference = new HashMap<>();
    static {
        operatorPreference.put('+', 1);
        operatorPreference.put('-', 1);
        operatorPreference.put('*', 2);
        operatorPreference.put('/', 2);
    }

    private static final Set<Character> opers = new HashSet<>(Arrays.asList('(', ')', '+', '-', '*', '/'));

    /**
     * Build an Infix-postfix expression by using an operatorStack for precedence computation.
     *
     * @param input
     * @return
     */
    public int calculate(String input) {
        char[] array = input.toCharArray();
        Stack<Character> operatorStack = new Stack<>();
        List<Object> postfixExpression = new ArrayList<>();

        int index = 0;
        while (index < array.length) {
            char c = array[index++];
            if (c == ' ') continue;

            if (!opers.contains(c)) {
                /**
                 * number
                 */
                int sum = Character.digit(c, 10);
                while (index < array.length && !opers.contains(array[index]) && array[index] != ' ') {
                    int num = Character.digit(array[index], 10);
                    sum = sum * 10 + num;
                    index++;
                }
                postfixExpression.add(sum);
            } else if (c == '(') {
                /**
                 * Push to operator stack
                 */
                operatorStack.push(c);
            } else if (c == ')') {
                /**
                 * pop from operator-stack and add to postfix, until we encounter a matching (
                 */
                while (!operatorStack.isEmpty()) {
                    final char t = operatorStack.pop();
                    if (t == '(') {
                        break;
                    } else {
                        postfixExpression.add(t);
                    }
                }
            } else {
                /**
                 * Pop all operators that are of higher-precedence than current operator and add to postfix.
                 * Push current operator to stack.
                 */
                while (!operatorStack.isEmpty()) {
                    final Character top = operatorStack.peek();
                    if (top.charValue() == '(') break;
                    if (operatorPreference.get(top) >= operatorPreference.get(c)) {
                        postfixExpression.add(operatorStack.pop());
                    } else {
                        break;
                    }
                }
                operatorStack.push(c);
            }
        }

        /**
         * Pop the remaining operators from stack and add to postfix.
         */
        while (!operatorStack.isEmpty()) {
            postfixExpression.add(operatorStack.pop());
        }

        return evalExpression(postfixExpression);
    }

    private int evalExpression(List<Object> input) {
        Stack<Integer> result = new Stack<>();
        for (Object obj : input) {
            if (obj instanceof Integer) {
                result.push((Integer) obj);
            } else {
                char operator = (Character) obj;
                int oper2 = result.pop();
                int oper1 = result.pop();

                switch (operator) {
                case '+':
                    result.push(oper1 + oper2);
                    break;
                case '-':
                    result.push(oper1 - oper2);
                    break;
                case '*':
                    result.push(oper1 * oper2);
                    break;
                case '/':
                    result.push(oper1 / oper2);
                    break;
                default:
                    break;
                }
            }
        }
        return result.pop();
    }

    public static void main(String[] args) {
        System.out.println(new BasicCalculatorII().calculate(" 3/2 "));
    }
}
