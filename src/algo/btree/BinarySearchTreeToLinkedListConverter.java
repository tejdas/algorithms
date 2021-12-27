package algo.btree;

import java.util.Stack;

/**
 * Leetcode 426
 */
public class BinarySearchTreeToLinkedListConverter {
    static TreeNode prevNode = null;
    static TreeNode first = null;

    static void convertBSTToDoubleLinkedListNonRecurse(final TreeNode root) {
        prevNode = null;
        first = null;

        final Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;

        /*
         * Go all the way to the left subtree
         */
        while (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            convertLinks(curNode);

            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();

                convertLinks(curNode);
            }
            curNode = curNode.right;
        }
    }

    static TreeNode treeToDoublyList(TreeNode root) {
        prevNode = null;
        first = null;

        final Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;

        /*
         * Go all the way to the left subtree
         */
        while (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            convertLinks(curNode);

            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();

                convertLinks(curNode);
            }
            curNode = curNode.right;
        }

        if (first != null) {
            first.left = prevNode;
            prevNode.right = first;
        }
        return first;
    }

    private static void convertLinks(TreeNode curNode) {
        if (first == null) {
            first = curNode;
            curNode.left = null;
        } else {
            prevNode.right = curNode;
            curNode.left = prevNode;
        }
        prevNode = curNode;
    }

    public static void main(final String[] args) {
        final Integer[] array = new Integer[] { 10, 7, 5, 9, 8, 15, 12, 11, 13, 18, 20 };
        TreeNode root = BinarySearchTreeBuilder.buildBSTFromPreOrderList(array);

        prevNode = null;
        first = null;
        root = BinarySearchTreeBuilder.buildBSTFromPreOrderList(array);
        root.traverseInOrder();

        System.out.println();
        TreeNode root2 = treeToDoublyList(root);
        TreeNode root3 = root2;
        do {
            System.out.print(root2.value + "  ");
            root2 = root2.right;
        } while (root2 != root3);

    }
}
