package algo.btree;

import java.util.Iterator;
import java.util.Stack;

/**
 * Links a Binary Tree such that we can perform
 * InOrder traversal by following the link (linkedList)
 * @author tdas
 */
public class InorderTraverser {
    private static class TreeNode {
        public TreeNode(final int value) {
            super();
            this.value = value;
        }
        int value;
        TreeNode left = null;
        TreeNode right = null;
        TreeNode parent = null;
        /*
         * Points to next node in the inorder traversal
         */
        TreeNode nextInorder = null;
    }

    /*
     * Builds BinaryTree, such that each RandomListNode's parent is set.
     */
    private static TreeNode buildTree(final int[] array, final int from, final int to) {
        if (from == to) {
            return new TreeNode(array[from]);
        } else {
            final int index = (from+to) / 2;
            final TreeNode treeNode = new TreeNode(array[index]);
            if (index-1 >= from) {
                treeNode.left = buildTree(array, from, index-1);
                treeNode.left.parent = treeNode;
            }
            if (to >= index+1) {
                treeNode.right = buildTree(array, index+1, to);
                treeNode.right.parent = treeNode;
            }
            return treeNode;
        }
    }

    private static TreeNode linkInorderTraversal(final TreeNode root) {
        /*
         * cur follows the in-order route.
         */
        TreeNode cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        final TreeNode first = cur;

        while (true) {
            if (cur.right != null) {
                /*
                 * Go to right-child, and then
                 * traverse all the way following
                 * left. That node is next in-order.
                 */
                TreeNode next = cur.right;
                while (next.left != null) {
                    next = next.left;
                }
                cur.nextInorder = next;
                cur = next;
            } else if (cur.parent != null) {
                /*
                 * No right node. Now return to parent.
                 */
                if (cur == cur.parent.left) {
                    /*
                     * If current node is parent's left node,
                     * then parent node is next in-order.
                     */
                    cur.nextInorder = cur.parent;
                    cur = cur.parent;
                } else if (cur == cur.parent.right) {
                    /*
                     * If current node is parent's right node,
                     * follow parent links to traverse up the
                     * tree until we find a node that is the left
                     * node of it's parent.
                     *
                     * If a parent is found, it is next in-order.
                     * Otherwise, we have linked the in-order route.
                     */
                    TreeNode next = cur;
                    while ((next.parent != null) && (next == next.parent.right)) {
                        next = next.parent;
                    }
                    if (next.parent == null) {
                        return first;
                    } else if (next == next.parent.left) {
                        cur.nextInorder = next.parent;
                        cur = next.parent;
                    }
                }
            }
        }
    }

    static void traverseMorrisInorder(final TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            while (cur.left != null) {
                TreeNode temp = cur.left;
                while (temp.right != null && temp.right != cur) {
                    temp = temp.right;
                }
                if (temp.right == null) {
                    temp.right = cur;
                    cur = cur.left;
                } else {
                    System.out.print(cur.value + " ");
                    temp.right = null;
                    cur = cur.right;
                }
            }
            System.out.print(cur.value + " ");
            cur = cur.right;
        }


    }

    static void traverseInorder(final TreeNode cur) {
        if (cur.left != null)
            traverseInorder(cur.left);
        System.out.print(cur.value + " ");
        if (cur.right != null)
            traverseInorder(cur.right);
    }

    static TreeNode findNextInorder(final TreeNode current) {
        if (current.right != null) {
            TreeNode temp = current.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        TreeNode child = current;
        TreeNode parent = child.parent;
        while (parent != null && parent.right == child) {
            child = parent;
            parent = child.parent;
        }
        return parent;
    }

    static TreeNode findNode(final int val, final TreeNode root) {
        if (root.value == val) {
            return root;
        }

        if (root.left != null) {
            final TreeNode temp = findNode(val, root.left);
            if (temp != null) {
                return temp;
            }
        }

        if (root.right != null) {
            final TreeNode temp = findNode(val, root.right);
            return temp;
        }
        return null;
    }

    static boolean findPathToNode(final int val, final TreeNode curNode, final Stack<TreeNode> path) {
        path.push(curNode);
        if (curNode.value == val) {
            return true;
        }

        if (curNode.left != null) {
            if (findPathToNode(val, curNode.left, path)) {
                return true;
            }
            path.pop();
        }

        if (curNode.right != null) {
            if (findPathToNode(val, curNode.right, path)) {
                return true;
            }
            path.pop();
        }
        return false;
    }

    public static void main1(final String[] args) {
        final int[] array = {1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120,125, 130, 135, 140, 145, 150};
        final TreeNode root = buildTree(array, 0, array.length-1);

        TreeNode inOrderP = linkInorderTraversal(root);
        while (inOrderP != null) {
            System.out.print("  " + inOrderP.value);
            inOrderP = inOrderP.nextInorder;
        }
    }

    static TreeNode commonAncestor(final TreeNode root, final int valA, final int valB) {

        final Stack<TreeNode> stackA = new Stack<>();

        final Stack<TreeNode> stackB = new Stack<>();

        final boolean flagA = findPathToNode(valA, root, stackA);
        final boolean flagB = findPathToNode(valB, root, stackB);

        if (!flagA || !flagB) {
            return null;
        }

        for (final TreeNode val : stackA) System.out.print(val.value + "  ");
        System.out.println();

        for (final TreeNode val : stackB) System.out.print(val.value + "  ");
        System.out.println();


        final Iterator<TreeNode> iterA = stackA.iterator();
        final Iterator<TreeNode> iterB = stackB.iterator();

        TreeNode common = null;
        while (iterA.hasNext() && iterB.hasNext()) {
            final TreeNode tA = iterA.next();
            final TreeNode tB = iterB.next();

            System.out.println(tA.value + " " + tB.value);
            if (tA == tB) {
                common = tA;
            } else {
                return common;
            }
        }

        return null;
    }

    public static void main2(final String[] args) {
        final int[] array = {1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120,125, 130, 135, 140, 145, 150};
        final TreeNode root = buildTree(array, 0, array.length-1);

        traverseMorrisInorder(root);
        System.out.println();

        int val = array[0];

        final TreeNode uber = new TreeNode(-1);
        uber.right = root;
        root.parent = uber;
        TreeNode cur = uber;
        while (true) {
            final TreeNode next = findNextInorder(cur);
            if (next == null) {
                break;
            }
            val = next.value;
            System.out.print(val + " ");
            cur = next;
        }
        System.out.println();

        final TreeNode ancestor = commonAncestor(root, 4, 65);
        if (ancestor != null) {
            System.out.println("ancestor: " + ancestor.value);
        }
    }

    public static void main(final String[] args) {
        final int[] array = { 1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100,
            105, 110, 115, 120, 125, 130, 135, 140, 145, 150 };
        final TreeNode root = buildTree(array, 0, array.length - 1);

        traverseMorrisInorder(root);
        System.out.println();
    }
}
