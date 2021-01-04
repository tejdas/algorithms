package algo.btree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import xxx.yyy.Util;

/**
 * Creates a Binary Tree, by reading information in the form of a list of
 * [nodeValue, hasLeftSubtree, hasRightSubtree]
 *
 * @author tejdas
 */
public class BinaryTreeBuilder {
    static TreeNode buildTree(final int[] array, final int from, final int to) {
        if (from == to) {
            return new TreeNode(array[from]);
        } else {
            final int index = (from + to) / 2;
            final TreeNode treeNode = new TreeNode(array[index]);
            if (index - 1 >= from) {
                treeNode.left = buildTree(array, from, index - 1);
            }
            if (to >= index + 1) {
                treeNode.right = buildTree(array, index + 1, to);
            }
            return treeNode;
        }
    }

    static class TreeInfo {
        public TreeInfo(final int value, final boolean hasLeft, final boolean hasRight) {
            super();
            this.value = value;
            this.hasLeft = hasLeft;
            this.hasRight = hasRight;
        }

        public int value;

        public boolean hasLeft;

        public boolean hasRight;
    }

    public static TreeNode buildTree(final Queue<TreeInfo> inputs) {
        if (inputs.isEmpty()) {
            return null;
        }
        final TreeInfo input = inputs.remove();
        final TreeNode node = new TreeNode(input.value);
        if (input.hasLeft) {
            node.left = buildTree(inputs);
        }
        if (input.hasRight) {
            node.right = buildTree(inputs);
        }
        return node;
    }

    public static void tracePathsToLeafNodes(final TreeNode root) {
        final Stack<Integer> path = new Stack<>();
        System.out.println("Tracing path to leaf");
        tracePathToLeaf(root, path);
    }

    private static void tracePathToLeaf(final TreeNode node, final Stack<Integer> path) {
        path.push(node.value);
        if (node.left == null && node.right == null) {
            Util.printArray(path.toArray(new Integer[path.size()]));
        }
        if (node.left != null) {
            tracePathToLeaf(node.left, path);
        }
        if (node.right != null) {
            tracePathToLeaf(node.right, path);
        }
        path.pop();
    }

    public static String serialize(final TreeNode root) {
        final StringBuilder sb = new StringBuilder();
        serializeTree(root, sb);
        return "(" + sb.toString() + ')';
    }

    private static void serializeTree(final TreeNode node, final StringBuilder sb) {
        sb.append(String.valueOf(node.value));
        if (node.left != null) {
            sb.append('(');
            serializeTree(node.left, sb);
            sb.append(')');
        } else {
            sb.append("L");
        }
        if (node.right != null) {
            sb.append('{');
            serializeTree(node.right, sb);
            sb.append('}');
        } else {
            sb.append("R");
        }
    }

    public static TreeNode deserialize(final String s) {
        return deserializeTree(s);
    }

    static final List<Character> SPECIAL_CHARS = Arrays.asList('(', ')', '{',
            '}', 'L', 'R');

    private static TreeNode deserializeTree(final String input) {
        TreeNode root = null;
        final Stack<TreeNode> stack = new Stack<>();
        final char[] array = input.toCharArray();
        int pos = 0;
        while (pos < array.length) {
            if (array[pos] == '(' || array[pos] == '{') {
                final boolean isLeft = (array[pos] == '(');
                pos++;
                final StringBuilder num = new StringBuilder();
                while (!SPECIAL_CHARS.contains(array[pos])) {
                    num.append(array[pos]);
                    pos++;
                }
                if (num != null) {
                    final int val = Integer.valueOf(num.toString());
                    final TreeNode node = new TreeNode(val);
                    if (root == null) root = node;
                    if (!stack.isEmpty()) {
                        if (isLeft) {
                            stack.peek().left = node;
                        } else {
                            stack.pop().right = node;
                        }
                    }
                    stack.push(node);
                }
                continue;
            } else if (array[pos] == 'R') {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
            pos++;
        }
        return root;
    }

    static void printNodesWithoutSibling(final TreeNode node) {
        if (node.left != null && node.right == null) {
            System.out.println(node.left.value);
        } else if (node.left == null && node.right != null) {
            System.out.println(node.right.value);
        }
        if (node.left != null) {
            printNodesWithoutSibling(node.left);
        }

        if (node.right != null) {
            printNodesWithoutSibling(node.right);
        }
    }

    static boolean isCompleteTree(final TreeNode root) {
        final Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode incompleteNode = null;
        while (!queue.isEmpty()) {
            final TreeNode cur = queue.poll();
            if (cur.left==null && cur.right!=null) {
                return false;
            }
            if (cur.left!=null && cur.right==null) {
                incompleteNode = cur;
                break;
            }
            if (cur.left != null)
                queue.add(cur.left);

            if (cur.right != null)
                queue.add(cur.right);
        }
        if (incompleteNode == null)
            return true;

        queue.clear();

        queue.add(root);
        TreeNode cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left==null && cur.right!=null) {
                return false;
            }
            if (cur.left != null)
                queue.add(cur.left);

            if (cur.right != null)
                queue.add(cur.right);
        }
        if (cur == incompleteNode.left) {
            return true;
        }
        return false;
    }

    private static class TreeInorderIterator {
        public TreeInorderIterator(final TreeNode root) {
            super();
            this.root = root;
        }
        private final TreeNode root;
        private TreeNode curNode;
        private final Stack<TreeNode> stack = new Stack<>();

        public TreeNode getFirst() {
            curNode = new TreeNode();
            curNode.value = -1;
            curNode.right = root;
            return curNode;
        }

        public TreeNode getNext() {
            if (curNode == null) {
                return null;
            }

            if (curNode.right == null) {
                if (!stack.isEmpty()) {
                    curNode = stack.pop();
                    return curNode;
                } else {
                    curNode = null;
                    return curNode;
                }
            } else {
                TreeNode current = curNode.right;

                while (current.left != null) {
                    stack.push(current);
                    current = current.left;
                }
                curNode = current;
                return curNode;
            }
        }
    }

    public static void main1(final String[] args) {
        final Queue<TreeInfo> inputs = new LinkedList<>();
        inputs.add(new TreeInfo(1, true, true));
        inputs.add(new TreeInfo(2, true, true));
        inputs.add(new TreeInfo(3, false, false));
        inputs.add(new TreeInfo(4, true, true));
        inputs.add(new TreeInfo(5, false, false));
        inputs.add(new TreeInfo(6, true, true));
        inputs.add(new TreeInfo(7, true, false));
        inputs.add(new TreeInfo(8, true, false));
        inputs.add(new TreeInfo(9, false, false));
        inputs.add(new TreeInfo(10, false, false));
        inputs.add(new TreeInfo(11, true, true));
        inputs.add(new TreeInfo(12, false, false));
        inputs.add(new TreeInfo(13, true, true));
        inputs.add(new TreeInfo(14, false, true));

        inputs.add(new TreeInfo(18, true, false));
        inputs.add(new TreeInfo(19, true, true));
        inputs.add(new TreeInfo(20, true, false));
        inputs.add(new TreeInfo(21, true, false));
        inputs.add(new TreeInfo(22, false, true));
        inputs.add(new TreeInfo(23, false, false));
        inputs.add(new TreeInfo(24, false, false));

        inputs.add(new TreeInfo(15, false, true));
        inputs.add(new TreeInfo(16, true, false));
        inputs.add(new TreeInfo(17, false, false));

        final Queue<TreeInfo> inputs2 = new LinkedList<>();
        inputs2.addAll(inputs);

        final Queue<TreeInfo> inputs3 = new LinkedList<>();
        for (final TreeInfo input : inputs) {
            if (input.value != 23 && input.value != 17) {
                inputs3.add(input);
            }
        }

        final TreeNode root = buildTree(inputs);
        root.traversePreOrder();
        System.out.println();
        root.traverseInOrder();
        System.out.println();
        TreeNode.traverseInOrderNonRecurse(root);

        final TreeNode root2 = buildTree(inputs2);
        boolean matches = BinaryTreeSubsetFinder.isExactMatch(root, root2);
        System.out.println(matches);

        final TreeNode root3 = buildTree(inputs3);
        matches = BinaryTreeSubsetFinder.isExactMatch(root, root3);
        System.out.println(matches);

        System.out.println("======================");
        tracePathsToLeafNodes(root);
        System.out.println("======================");
        final String ser = serialize(root);
        System.out.println(ser);

        final TreeNode root5 = deserialize(ser);
        root5.traversePreOrder();
        System.out.println();
        final String ser2 = serialize(root5);
        System.out.println(ser2);
        if (ser.equalsIgnoreCase(ser2))
            System.out.println("Equal");
        else
            System.out.println("Not Equal");
    }
    public static void main2(final String[] args) {
        final Queue<TreeInfo> inputs = new LinkedList<>();
        inputs.add(new TreeInfo(1, true, true));
        inputs.add(new TreeInfo(2, true, true));
        inputs.add(new TreeInfo(3, false, false));
        inputs.add(new TreeInfo(4, true, true));
        inputs.add(new TreeInfo(5, false, false));
        inputs.add(new TreeInfo(6, true, true));
        inputs.add(new TreeInfo(7, true, false));
        inputs.add(new TreeInfo(8, true, false));
        inputs.add(new TreeInfo(9, false, false));
        inputs.add(new TreeInfo(10, false, false));
        inputs.add(new TreeInfo(11, true, true));
        inputs.add(new TreeInfo(12, false, false));
        inputs.add(new TreeInfo(13, true, true));
        inputs.add(new TreeInfo(14, false, true));

        inputs.add(new TreeInfo(18, true, false));
        inputs.add(new TreeInfo(19, true, true));
        inputs.add(new TreeInfo(20, true, false));
        inputs.add(new TreeInfo(21, true, false));
        inputs.add(new TreeInfo(22, false, true));
        inputs.add(new TreeInfo(23, false, false));
        inputs.add(new TreeInfo(24, false, false));

        inputs.add(new TreeInfo(15, false, true));
        inputs.add(new TreeInfo(16, true, false));
        inputs.add(new TreeInfo(17, false, false));

        final TreeNode root = buildTree(inputs);
        printNodesWithoutSibling(root);

        final boolean isComplete = isCompleteTree(root);
        System.out.println(isComplete);
    }

    public static void main(final String[] args) {
        final Queue<TreeInfo> inputs = new LinkedList<>();
        inputs.add(new TreeInfo(1, true, true));
        inputs.add(new TreeInfo(2, true, true));
        inputs.add(new TreeInfo(3, false, false));
        inputs.add(new TreeInfo(4, true, true));
        inputs.add(new TreeInfo(5, false, false));
        inputs.add(new TreeInfo(6, true, true));
        inputs.add(new TreeInfo(7, true, false));
        inputs.add(new TreeInfo(8, true, false));
        inputs.add(new TreeInfo(9, false, false));
        inputs.add(new TreeInfo(10, false, false));
        inputs.add(new TreeInfo(11, true, true));
        inputs.add(new TreeInfo(12, false, false));
        inputs.add(new TreeInfo(13, true, true));
        inputs.add(new TreeInfo(14, false, true));

        inputs.add(new TreeInfo(18, true, false));
        inputs.add(new TreeInfo(19, true, true));
        inputs.add(new TreeInfo(20, true, false));
        inputs.add(new TreeInfo(21, true, false));
        inputs.add(new TreeInfo(22, false, true));
        inputs.add(new TreeInfo(23, false, false));
        inputs.add(new TreeInfo(24, false, false));

        inputs.add(new TreeInfo(15, false, true));
        inputs.add(new TreeInfo(16, true, false));
        inputs.add(new TreeInfo(17, false, false));

        final TreeNode root = buildTree(inputs);
        root.traverseInOrder();
        System.out.println();

        final TreeInorderIterator iter = new TreeInorderIterator(root);

        TreeNode node = iter.getFirst();
        while (node != null) {
            if (node.value != -1)
                System.out.print(node.value + "  ");
            node = iter.getNext();
        }
        System.out.println();


        TreeNode head = TreeNode.createCircularList(root);
        System.out.println();

        while (head != null) {
            System.out.print(head.value + "  ");
            head = head.right;
        }

    }

    public static void main4(final String[] args) {
        final Queue<TreeInfo> inputs = new LinkedList<>();
        inputs.add(new TreeInfo(1, true, true));
        inputs.add(new TreeInfo(2, true, true));
        inputs.add(new TreeInfo(3, true, false));
        inputs.add(new TreeInfo(14, false, false));
        inputs.add(new TreeInfo(4, false, false));
        inputs.add(new TreeInfo(11, false, true));
        //inputs.add(new TreeInfo(12, false, false));
        inputs.add(new TreeInfo(13, false, false));

        final TreeNode root = buildTree(inputs);

        final boolean isComplete = isCompleteTree(root);
        System.out.println(isComplete);
    }
}
