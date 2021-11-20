package algo.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 96
 * Dynamic Programming
 * Binary Tree
 */
public class UniqueBinarySearchTreeII {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private List<TreeNode>[][] memoized;
    private int n;
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) return Collections.emptyList();
        if (n == 1) return Collections.singletonList(new TreeNode(n));
        this.n = n;

        memoized = new List[n+1][n+1];

        return numBST(1, n);
    }

    private List<TreeNode> numBST(int left, int right) {
        if (left > right) return null;
        if (left < 1 || right > n) return null;

        if (memoized[left][right] != null)
            return memoized[left][right];

        if (left == right) {
            TreeNode treeNode = new TreeNode(left);
            memoized[left][right] = Collections.singletonList(treeNode);
            return memoized[left][right];
        }

        List<TreeNode> list = new ArrayList<>();
        if (left == right-1) {
            {
                TreeNode root = new TreeNode(right);
                root.left = new TreeNode(left);
                list.add(root);
            }

            {
                TreeNode root = new TreeNode(left);
                root.right = new TreeNode(right);
                list.add(root);
            }
            memoized[left][right] = list;
            return memoized[left][right];
        }

        for (int k = left; k <= right; k++) {
            if (k == left) {

                List<TreeNode> rightNodes = numBST(k+1, right);
                for (TreeNode n : rightNodes) {
                    TreeNode root = new TreeNode(k);
                    root.right = n;
                    list.add(root);
                }
            } else if (k == right) {
                List<TreeNode> leftNodes = numBST(left, k-1);
                for (TreeNode n : leftNodes) {
                    TreeNode root = new TreeNode(k);
                    root.left = n;
                    list.add(root);
                }

            } else {
                List<TreeNode> leftNodes = numBST(left, k-1);
                List<TreeNode> rightNodes = numBST(k + 1, right);
                for (TreeNode ln : leftNodes) {
                    for (TreeNode rn : rightNodes) {
                        TreeNode root = new TreeNode(k);
                        root.left = ln;
                        root.right = rn;
                        list.add(root);
                    }
                }
            }
        }

        memoized[left][right] = list;
        return memoized[left][right];
    }
}
