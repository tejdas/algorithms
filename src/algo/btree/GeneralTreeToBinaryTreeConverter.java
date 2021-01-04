package algo.btree;

import java.util.LinkedList;

public class GeneralTreeToBinaryTreeConverter {

    static class GeneralTreeNode {
        public GeneralTreeNode(final char val) {
            super();
            this.val = val;
        }
        public char val;
        private final LinkedList<GeneralTreeNode> children = new LinkedList<>();

        public void addChildren(final char[] param) {
            for (final char child : param) {
                children.add(new GeneralTreeNode(child));
            }
        }
    }

    static class BinaryTreeNode {
        public BinaryTreeNode(final char val) {
            super();
            this.val = val;
        }
        public char val;
        public BinaryTreeNode left;
        public BinaryTreeNode right;
    }

    static BinaryTreeNode buildNode(final GeneralTreeNode genericNode, final LinkedList<GeneralTreeNode> siblings) {
        final BinaryTreeNode node = new BinaryTreeNode(genericNode.val);

        if (!genericNode.children.isEmpty()) {
            final LinkedList<GeneralTreeNode> children = new LinkedList<>(genericNode.children);
            final GeneralTreeNode leftMostChild = children.remove();
            node.left = buildNode(leftMostChild, children);
        }

        if (!siblings.isEmpty()) {
            final GeneralTreeNode leftmostSibling = siblings.remove();
            node.right = buildNode(leftmostSibling, siblings);
        }

        return node;
    }

    static void inorder(final BinaryTreeNode node) {
        if (node.left != null) {
            inorder(node.left);
        }
        System.out.println(node.val);

        if (node.right != null) {
            inorder(node.right);
        }

    }

    /**
     * @param args
     */
    public static void main(final String[] args) {

        final GeneralTreeNode nodeA = new GeneralTreeNode('a');
        final GeneralTreeNode nodeB = new GeneralTreeNode('b');
        final GeneralTreeNode nodeC = new GeneralTreeNode('c');
        final GeneralTreeNode nodeD = new GeneralTreeNode('d');
        final GeneralTreeNode nodeF = new GeneralTreeNode('f');

        nodeA.addChildren(new char[] {'b', 'c', 'd'});
        nodeB.addChildren(new char[] {'k'});
        nodeC.addChildren(new char[] {'h', 'i', 'j'});
        nodeD.addChildren(new char[] {'e', 'f'});
        nodeF.addChildren(new char[] {'g'});

        final BinaryTreeNode root = buildNode(nodeA, new LinkedList<GeneralTreeNode>());
        inorder(root);
    }
}
