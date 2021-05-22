package net.lc;

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
            return leftRes;
        }

        LCAResult rightRes = lca(curNode.right, targetA, targetB);
        if (rightRes.nodeCount == 2) {
            return rightRes;
        }

        int totalCount = 0;
        if (curNode == targetA || curNode == targetB) {
            totalCount++;
        }
        totalCount += leftRes.nodeCount;
        totalCount += rightRes.nodeCount;

        return new LCAResult(totalCount, (totalCount == 2)? curNode : null);
    }

    public TreeNode lowestCommonAncestor(final TreeNode curNode, final TreeNode targetA, final TreeNode targetB) {
        LCAResult res = lca(curNode, targetA, targetB);
        return res.parent;
    }
}
