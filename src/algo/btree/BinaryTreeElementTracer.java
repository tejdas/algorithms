package algo.btree;

import java.util.Stack;


/**
 * Traces any given element, and finds the direction
 * starting from root.
 *
 * Uses a stack data structure to store the path.
 *
 * @author tejdas
 *
 */
public class BinaryTreeElementTracer {
    private enum Direction {
        Left, Right
    };

    private static final class PathDetail {
        public PathDetail(Direction direction, int val) {
            super();
            this.direction = direction;
            this.val = val;
        }

        Direction direction;
        int val;
    }

    /**
     * Recursively trace an element, using
     * a stack.
     * @param curNode
     * @param path
     * @param desiredNode
     * @return
     */
    private static boolean traceElement(TreeNode curNode,
            Stack<PathDetail> path, int desiredNode) {
        if (curNode.value == desiredNode) {
            if (path.isEmpty()) {
                System.out.println("Found root: " + desiredNode);
            }
            return true;
        }

        boolean found = false;
        if (curNode.left != null) {
            path.push(new PathDetail(Direction.Left, curNode.left.value));
            found = traceElement(curNode.left, path, desiredNode);
            if (found) {
                return true;
            } else {
                path.pop();
            }
        }

        if (curNode.right != null) {
            path.push(new PathDetail(Direction.Right, curNode.right.value));
            found = traceElement(curNode.right, path, desiredNode);
            if (found) {
                return true;
            } else {
                path.pop();
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[] array = { 1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 90, 95 };
        TreeNode root = BinaryTreeBuilder.buildTree(array, 0, array.length - 1);
        for (int val : array) {
            Stack<PathDetail> path = new Stack<>();
            if (traceElement(root, path, val)) {
                for (PathDetail pd : path) {
                    System.out.print(pd.direction + "  " + pd.val + " ");
                }
            } else {
                System.out.println("path not found for: " + val);
            }
            System.out.println();
        }
    }
}
