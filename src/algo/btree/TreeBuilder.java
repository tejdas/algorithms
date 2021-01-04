package algo.btree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TreeBuilder {

    static class TNode {
        int val;
        TNode left = null;
        TNode right = null;
    }

    static class Pair {
        int left;
        int right;

        public Pair(final int l, final int r) {
            left = l;
            right = r;
        }

        public Pair() {
            left = Integer.MAX_VALUE;
            right = Integer.MAX_VALUE;
        }
    }

    private final Map<Integer, Pair> nodeInfoMap = new HashMap<>();

    public void addNodeInfo(final int node, final int parent, final boolean isLeft) {
        Pair p = nodeInfoMap.get(parent);
        if (p == null) {
            p = new Pair();
            nodeInfoMap.put(parent, p);
        }
        if (isLeft) {
            p.left = node;
        } else {
            p.right = node;
        }
    }

    private TNode construct() {
        final Pair p = nodeInfoMap.get(-1);
        if (p == null) {
            return null;
        }

        final TNode root = new TNode();
        if (p.left == Integer.MAX_VALUE) {
            root.val = p.right;
        } else {
            root.val = p.left;
        }

        final Queue<TNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final TNode node = queue.poll();

            final Pair pp = nodeInfoMap.get(node.val);
            if (pp == null) {
                continue;
            }
            if (pp.left == Integer.MAX_VALUE) {
                node.left = null;
            } else {
                final TNode left = new TNode();
                node.left = left;
                left.val = pp.left;
                queue.add(left);
            }

            if (pp.right == Integer.MAX_VALUE) {
                node.right = null;
            } else {
                final TNode right = new TNode();
                node.right = right;
                right.val = pp.right;
                queue.add(right);
            }
        }
        return root;
    }

    public void preorder(final TNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val);
        preorder(node.left);
        preorder(node.right);
    }

    public void inorder(final TNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.println(node.val);
        inorder(node.right);
    }


    public static void main(final String[] args) {
        final TreeBuilder tb = new TreeBuilder();

        tb.addNodeInfo(15, 20, true);
        tb.addNodeInfo(19, 80, true);
        tb.addNodeInfo(17, 20, false);
        tb.addNodeInfo(16, 80, false);
        tb.addNodeInfo(80, 50, false);
        tb.addNodeInfo(50, -1, false);
        tb.addNodeInfo(20, 50, true);
        tb.addNodeInfo(21, 16, false);
        tb.addNodeInfo(22, 21, true);

        final TNode root = tb.construct();

        tb.inorder(root);
    }
}
