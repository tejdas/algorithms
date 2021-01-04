package algo.btree;

import java.util.ArrayList;
import java.util.List;


public class BinaryTreeSameLevelLinkedListCreator {

    /**
     * Links Nodes from left to right at the same-level
     * in a BinaryTree via next.
     *
     * @param head first element in the LinkedList, in the current level.
     * @return first element (head) in the LinkedList, at the next level.
     */
    private static TreeNode treeLinkedList(final TreeNode head) {
        TreeNode headAtBelowLevel = null;

        /*
         * Iterate at the current level, starting from head.
         * For each node at the current level, link it's children.
         *
         * curNode points to current node
         * curNodeAtBelowLevel points to current node at the next level
         * (whose next pointer would point to one of
         * curNode's children.
         */
        TreeNode curNode = head;
        TreeNode curNodeAtBelowLevel = null;
        while (curNode != null) {
            if (curNodeAtBelowLevel != null) {
                /*
                 * prev node's next points
                 */
                if (curNode.left != null) {
                    curNodeAtBelowLevel.next = curNode.left;
                } else if (curNode.right != null) {
                    curNodeAtBelowLevel.next = curNode.right;
                }
            }

            if (curNode.left != null && curNode.right != null) {
                curNode.left.next = curNode.right;
                curNodeAtBelowLevel = curNode.right;
                if (headAtBelowLevel == null) {
                    headAtBelowLevel = curNode.left;
                }
            } else if (curNode.left != null) {
                curNodeAtBelowLevel = curNode.left;
                if (headAtBelowLevel == null) {
                    headAtBelowLevel = curNode.left;
                }
            } else if (curNode.right != null) {
                curNodeAtBelowLevel = curNode.right;
                if (headAtBelowLevel == null) {
                    headAtBelowLevel = curNode.right;
                }
            }
            curNode = curNode.next;
        }
        return headAtBelowLevel;
    }

    private static List<TreeNode> linkTree(final TreeNode root) {
        final List<TreeNode> heads = new ArrayList<>();
        /*
         * Start with root.
         * Then link nodes at each level, from left to right.
         */
        TreeNode headNextLevel = root;
        while (true) {
            heads.add(headNextLevel);
            headNextLevel = treeLinkedList(headNextLevel);
            if (headNextLevel == null) {
                break;
            }
        }
        return heads;
    }

    public static void main(final String[] args) {
        final int[] array = { 1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 90, 95 };
        final TreeNode root = BinaryTreeBuilder.buildTree(array, 0, array.length - 1);
        final List<TreeNode> heads = linkTree(root);

        for (final TreeNode head : heads) {
            TreeNode cur = head;
            while (cur != null) {
                System.out.print("  " + cur.value);
                cur = cur.next;
            }
            System.out.println();
        }
        TreeNode.traverseBFS(root);

        TreeNode.traverseBFSMultiline(root);
    }
}
