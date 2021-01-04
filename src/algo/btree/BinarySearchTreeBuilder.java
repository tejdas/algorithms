package algo.btree;

import java.util.Stack;

public class BinarySearchTreeBuilder {
    public static TreeNode buildBSTFromPreOrderList(final Integer[] array) {
        if ((array == null) || (array.length == 0)) {
            return null;
        }

        TreeNode root = null;
        final Stack<TreeNode> stack = new Stack<>();
        for (final int val : array) {
            final TreeNode node = new TreeNode(val);
            if (root == null) {
                root = node;
            }

            if (!stack.isEmpty()) {
                if (val < stack.peek().value) {
                    final TreeNode top = stack.peek();
                    top.left = node;
                } else {
                    while (!stack.isEmpty()) {
                        final TreeNode top = stack.pop();
                        if (stack.isEmpty()) {
                            top.right = node;
                            break;
                        } else {
                            if (val < stack.peek().value) {
                                top.right = node;
                                break;
                            }
                        }
                    }
                }
            }
            stack.push(node);
        }
        return root;
    }

    static void printNodeAndParent(final TreeNode root, final int value) {
        final TreeNode node = root.findNode(value);
        if (node != null) {
            final TreeNode parent = root.findParentNode(value);
            if (parent != null) {
                System.out.println("Node: " + node.value + " parent: "
                        + parent.value);
            }
        }
    }

    static TreeNode removeNodeFromBST(TreeNode root, final int value) {
        final TreeNode node = root.findNode(value);
        if (node == null) {
            System.out.println("Node could not be found: " + value);
            return root;
        }
        final TreeNode parent = (root==node)? null : root.findParentNode(value);
        if (node.left==null && node.right==null) {
            if (parent == null) {
                root = null;
            } else if (node == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (node.right != null) {
            final TreeNode rightNode = node.right;
            TreeNode nextInorder = rightNode;
            while (nextInorder.left != null) {
                nextInorder = nextInorder.left;
            }
            nextInorder.left = node.left;
            if (parent == null) {
                root = rightNode;
            } else if (node == parent.left) {
                parent.left = rightNode;
            } else {
                parent.right = rightNode;
            }
        } else {
            if (parent == null) {
                root = node.left;
            } else if (node == parent.left) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        }
        return root;
    }

    public static void insertNodeIntoBSTNonRecurse(final TreeNode root, final int val) {
        TreeNode nodeToInsert = root;
        while (true) {
            if (val < nodeToInsert.value) {
                if (nodeToInsert.left == null) {
                    nodeToInsert.left = new TreeNode(val);
                    return;
                } else {
                    nodeToInsert = nodeToInsert.left;
                }
            } else {
                if (nodeToInsert.right == null) {
                    nodeToInsert.right = new TreeNode(val);
                    return;
                } else {
                    nodeToInsert = nodeToInsert.right;
                }
            }
        }
    }

    static TreeNode last = null;
    static TreeNode first = null;
    public static void traverseInOrderAndCreateDoublyLinkedList(final TreeNode node) {
        if (node.left != null) {
            traverseInOrderAndCreateDoublyLinkedList(node.left);
        }
        node.left = last;
        if (last != null) {
            last.right = node;
        }
        last = node;
        if (first == null) {
            first = last;
        }
        if (node.right != null) {
            traverseInOrderAndCreateDoublyLinkedList(node.right);
        }
    }

    static int sum = 0;
    public static void incrementValuesByAllLargerValues(final TreeNode node) {
        if (node.right != null) {
            incrementValuesByAllLargerValues(node.right);
        }

        final int temp = node.value;
        node.value += sum;
        sum += temp;

        if (node.left != null) {
            incrementValuesByAllLargerValues(node.left);
        }
    }

    private static boolean found = false;
    public static int getNextNumber(final TreeNode node, final int val) {
        if (node == null) {
            return -1;
        }

        final int retVal = getNextNumber(node.left, val);
        if (retVal != -1) {
            return retVal;
        }

        if (found) {
            return node.value;
        }

        if (node.value == val) {
            found = true;
        }

        final int retVal2 = getNextNumber(node.right, val);
        if (retVal2 != -1) {
            return retVal2;
        }

        return -1;
    }

    public static void main1(final String[] args) {

        //Integer[] array = new Integer[] { 20, 10, 5, 15, 30, 25, 35};
        //Integer[] array = new Integer[] { 20, 10, 5, 3, 7, 9, 15, 14, 12, 18, 19, 30, 25, 23, 28, 27, 35, 33, 45, 41, 43, 47};
        //Integer[] array = new Integer[] { 20, 10, 5, 3, 7, 9, 15, 14, 12, 18, 19};
        //Integer[] array = new Integer[] { 20};
        final Integer[] array = new Integer[] { 20, 30, 25, 23, 28, 27, 35, 33, 45, 41, 43, 47};

        TreeNode root = buildBSTFromPreOrderList(array);
        insertNodeIntoBSTNonRecurse(root, 42);
        root.traversePreOrder();
        System.out.println();
        root.traverseInOrder();
        System.out.println();

        root = removeNodeFromBST(root, 20);
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        root.traversePreOrder();
        System.out.println();
        root.traverseInOrder();
        System.out.println();
    }

    public static void main2(final String[] args) {
        final Integer[] array = new Integer[] { 20, 30, 25, 23, 28, 27, 35, 33, 45, 41, 43, 47};
        TreeNode root = buildBSTFromPreOrderList(array);
        root.traverseInOrder();
        System.out.println();

        last = null;
        first = null;
        traverseInOrderAndCreateDoublyLinkedList(root);
        System.out.println("---------------");

        TreeNode cur1 = first;
        while (cur1 != null) {
            System.out.print(cur1.value + "   ");
            cur1 = cur1.right;
        }
        System.out.println();

        TreeNode cur2 = last;
        while (cur2 != null) {
            System.out.print(cur2.value + "   ");
            cur2 = cur2.left;
        }
        System.out.println();
    }

    public static void main3(final String[] args) {
        final Integer[] array = new Integer[] { 20, 30, 25, 23, 28, 27, 35, 33, 45, 41, 43, 47};
        final TreeNode root = buildBSTFromPreOrderList(array);
        root.traverseInOrder();
        System.out.println();
        incrementValuesByAllLargerValues(root);
        root.traverseInOrder();
        System.out.println();
    }

    public static void main4(final String[] args) {
        final Integer[] array = new Integer[] { 20, 30, 25, 23, 28, 27, 35, 33, 45, 41, 43, 47};
        final TreeNode root = buildBSTFromPreOrderList(array);
        root.traverseInOrder();
        System.out.println();

        for (final int i : array) {
            found = false;
            final int val = getNextNumber(root, i);
            System.out.println(val);
        }
    }
}
