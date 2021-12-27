package net.lc.binarytree;

import java.util.*;

public class CounterClockwiseTraversal {
    static class Node {
        Node left;
        Node right;
        int val;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * auxiliary Node
     */
    static class NodeInfo {
        Node node;
        int vLevel;
        int hLevel;

        public NodeInfo(Node node, int hLevel, int vLevel) {
            this.node = node;
            this.vLevel = vLevel;
            this.hLevel = hLevel;
        }
    }

    /**
     * BFS traversal to fill auxiliary info.
     * For each h-level, keep track of left-most (non-leaf) node and right-most (non-leaf) node.
     *
     * Then build the CC list with following info:
     * Left-most non-leaf nodes
     * All the leaves in in-order traversal
     * Right-most non-leaf nodes (in reverse-order)
     *
     * @param root
     */
    public void traverseCounterClockwise(Node root) {

        NodeInfo rInfo = new NodeInfo(root, 0, 0);
        Queue<NodeInfo> queue = new LinkedList<>();

        List<NodeInfo> boundaryNodes = new ArrayList<>();
        queue.add(rInfo);

        List<Node> leftBoundary = new ArrayList<>();
        List<Node> rightBoundary = new ArrayList<>();

        /**
         * used to mark left-most and right-most (boundary) non-leaf nodes
         */
        int lastSeenLevel = 0;
        NodeInfo lastSeenNode = null;

        // add root to left-boundary
        leftBoundary.add(root);

        /**
         * BFS traversal
         */
        while (!queue.isEmpty()) {
            NodeInfo cur = queue.remove();

            if (cur.node.left == null && cur.node.right == null) {

                if (cur.hLevel == lastSeenLevel+1 && lastSeenNode != null) {
                    /**
                     * We are starting a new level;
                     * lastSeenNode is the right-most of the previous level, so add it to right boundary
                     */
                    rightBoundary.add(lastSeenNode.node);
                }

                lastSeenLevel = cur.hLevel;
                lastSeenNode = cur;
                continue;
            }

            if (cur.hLevel == lastSeenLevel+1) {
                /**
                 * We are starting a new level;
                 * So, cur node is the left-most of current level. Add it to left-boundary
                 * lastSeenNode is the right-most of the previous level, so add it to right boundary
                 */
                leftBoundary.add(cur.node);

                if (lastSeenNode != null) {
                    rightBoundary.add(lastSeenNode.node);
                }
            }

            /**
             * update book-keeping
             */
            lastSeenLevel = cur.hLevel;

            //exclude root from this logic, as that was already added to leftBoundary
            lastSeenNode = (cur.node != root)? cur : null;

            if (cur.node.left != null) {
                queue.add(new NodeInfo(cur.node.left, cur.hLevel+1, cur.vLevel-1));
            }

            if (cur.node.right != null) {
                queue.add(new NodeInfo(cur.node.right, cur.hLevel+1, cur.vLevel+1));
            }
        }

        /**
         * Build the final list
         */
        List<Node> counterClockList = new ArrayList<>();

        /**
         * Add nodes from left-boundary
         */
        counterClockList.addAll(leftBoundary);
        /**
         * collect leaf-nodes in Inorder
         */
        collectLeafNodesInOrder(root, counterClockList);
        /**
         * Add nodes from right-boundary in reverse-order
         */
        for (int index = rightBoundary.size()-1; index >= 0; index--) {
            counterClockList.add(rightBoundary.get(index));
        }

        for (Node n : counterClockList) {
            System.out.println(n.val);
        }
    }

    private static void collectLeafNodesInOrder(Node cur, List<Node> list) {
        if (cur.left == null && cur.right == null) {
            list.add(cur);
            return;
        }

        if (cur.left != null) {
            collectLeafNodesInOrder(cur.left, list);
        }

        if (cur.right != null) {
            collectLeafNodesInOrder(cur.right, list);
        }
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);

        root.left = n2;
        root.right = n3;

        n2.left = n4;
        n2.right = n5;
        n4.left = n8;
        n3.left = n6;
        n3.right = n7;
        n7.left = n9;

        new CounterClockwiseTraversal().traverseCounterClockwise(root);
    }
}
