package net.lc.binarytree;

/**
 * 236
 * Binary Tree
 * Post-order
 */
public class LowestCommonAncestor {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    static class LCAResult {
        int nodeCount;
        TreeNode parent;

        public LCAResult(int nodeCount, TreeNode parent) {
            this.nodeCount = nodeCount;
            this.parent = parent;
        }
    }

    /**
     * LCAResult.parent stores the lowest ancestor parent of both nodes.
     * @param curNode
     * @param targetA
     * @param targetB
     * @return
     */
    private LCAResult lca(final TreeNode curNode, final TreeNode targetA, final TreeNode targetB) {
        if (curNode == null) {
            return new LCAResult(0, null);
        }

        LCAResult leftRes = lca(curNode.left, targetA, targetB);
        if (leftRes.nodeCount == 2) {
            /**
             * Found an LCA in the left-subtree
             */
            return leftRes;
        }

        LCAResult rightRes = lca(curNode.right, targetA, targetB);
        if (rightRes.nodeCount == 2) {
            /**
             * Found an LCA in the right-subtree
             */
            return rightRes;
        }

        int totalCount = 0;
        if (curNode == targetA || curNode == targetB) {
            totalCount++;
        }
        totalCount += leftRes.nodeCount;
        totalCount += rightRes.nodeCount;
        boolean isCurNodeLCA = (totalCount == 2);

        return new LCAResult(totalCount, isCurNodeLCA? curNode : null);
    }

    public TreeNode lowestCommonAncestor(final TreeNode curNode, final TreeNode targetA, final TreeNode targetB) {
        LCAResult res = lca(curNode, targetA, targetB);
        return res.parent;
    }
}
