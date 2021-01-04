package algo.btree;

import java.util.Stack;

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

        convertBSTToDoubleLinkedListNonRecurse(root);

        System.out.println();

        TreeNode cur1 = first;
        while (cur1 != null) {
            System.out.print(cur1.value + "   ");
            cur1 = cur1.right;
        }
        System.out.println();

        TreeNode cur2 = prevNode;
        while (cur2 != null) {
            System.out.print(cur2.value + "   ");
            cur2 = cur2.left;
        }

    }
}
