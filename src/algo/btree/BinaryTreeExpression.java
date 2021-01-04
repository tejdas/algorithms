package algo.btree;
import java.util.Stack;


public class BinaryTreeExpression {
    static void buildBinaryTreeExpression(StringBuilder sb, TreeNode node) {
        sb.append('(');
        if (node.left != null) {
            buildBinaryTreeExpression(sb, node.left);
        } else {
            sb.append('0');
        }

        if (node.right != null) {
            buildBinaryTreeExpression(sb, node.right);
        } else {
            sb.append('0');
        }
        sb.append(')');
    }

    private static void printStack(Stack<Integer> stack) {
        System.out.print("Stack contents: " );
        for (int val : stack) {
            System.out.print("  " + val);
        }
        System.out.println();
    }

    static int findDepth(String expr) {
        int maxDepth = 0;
        char[] array = expr.toCharArray();
        Stack<Integer> stack = new Stack<>();

        int curDepth = 0;

        for (char c : array) {
            if (c == '(') {
                if (stack.isEmpty()) {
                    stack.push(1); //printStack(stack);
                } else {
                    int val = stack.pop(); //printStack(stack);
                    if (val >= 2) {
                        return -1;
                    }
                    val++;
                    stack.push(val); //printStack(stack);
                }
                stack.push(0); //printStack(stack);
                /*
                 * increment curdepth.
                 */
                curDepth++;
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return -1;
                }
                stack.pop(); //printStack(stack);
                /*
                 * Capture curDepth as maxDepth, if it exceeds
                 * current maxDepth
                 */
                if (curDepth > maxDepth) {
                    maxDepth = curDepth;
                }
                /*
                 * Decrement curDepth, upon encountering ')'
                 * If curDepth < 0, it means more right braces
                 * encountered than left braces, which is an
                 * invalid expresssion.
                 */
                curDepth--;
                if (curDepth < 0) {
                    return -1;
                }
            } else if (c == '0') {
                if (stack.isEmpty()) {
                    return -1;
                }
                int val = stack.pop(); //printStack(stack);
                if (val >= 2) {
                    return -1;
                }
                val++;
                stack.push(val); //printStack(stack);
            } else {
                /*
                 * Only '(' '0' ')' are allowed
                 */
                return -1;
            }
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        int[] array = { 1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70 };
        TreeNode root = BinaryTreeBuilder.buildTree(
                array, 0, array.length - 1);

        StringBuilder sb = new StringBuilder();
        buildBinaryTreeExpression(sb, root);
        System.out.println(sb.toString());
        System.out.println(findDepth(sb.toString()));

        System.out.println(findDepth("(((00(00))((00)(00)))(((00)(00))((00)(00))))"));
        System.out.println(findDepth("(((0(00))((00)(00)))(((00)(00))((00)(00)))0)"));
        System.out.println(findDepth("(0(0(00)0)"));
        System.out.println(findDepth("((00)0)"));
    }
}
