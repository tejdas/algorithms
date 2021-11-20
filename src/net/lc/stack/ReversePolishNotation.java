package net.lc.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 150
 * stack
 */
public class ReversePolishNotation {
    private static final List<String> operators = Arrays.asList("+","-","*","/");

    public int evalRPN(String[] input) {

        Stack<Integer> result = new Stack<>();
        for (String token : input) {
            if (!operators.contains(token)) {
                int val = Integer.valueOf(token);
                result.push(val);
            } else {
                int oper2 = result.pop();
                int oper1 = result.pop();

                switch (token) {
                case "+":
                    result.push(oper1 + oper2);
                    break;
                case "-":
                    result.push(oper1 - oper2);
                    break;
                case "*":
                    result.push(oper1 * oper2);
                    break;
                case "/":
                    result.push(oper1 / oper2);
                    break;
                default:
                    break;
                }
            }
        }
        return result.pop();
    }
}
