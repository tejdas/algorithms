package aaa.bbb;

import java.util.Scanner;

public class BinaryTreeBuilder {

    static enum Side {
        Left, Right
    }

    static class BTreeNode {
        int val;
        BTreeNode leftSubtree;
        BTreeNode rightSubtree;

        public BTreeNode(int val) {
            this.val = val;
        }
    }

    public BTreeNode buildBinaryTree() {
        Scanner in = new Scanner(System.in);
        BTreeNode root = createTree(in, -1, Side.Left);
        in.close();
        return root;

    }

    /**
     * PreOrder
     * @param in
     * @param parNode
     * @param side
     * @return
     */
    private BTreeNode createTree(Scanner in, int parNode, Side side) {

        if (parNode >= 0) {
            System.out.println("Creating: " + side + " child of: " + parNode);
        }

        System.out.println("Enter node");
        String s = in.nextLine();
        int val = Integer.parseInt(s);

        if (val < 0) return null;

        BTreeNode node = new BTreeNode(val);

        node.leftSubtree = createTree(in, val, Side.Left);

        node.rightSubtree = createTree(in, val, Side.Right);

        return node;
    }

    private void printTree(BTreeNode node) {
        if (node == null) return;
        printTree(node.leftSubtree);
        System.out.println(node.val);
        printTree(node.rightSubtree);
    }

    public static void main(String[] args) {
        BinaryTreeBuilder treeBuilder = new BinaryTreeBuilder();
        BTreeNode root = treeBuilder.buildBinaryTree();
        treeBuilder.printTree(root);
    }
}
