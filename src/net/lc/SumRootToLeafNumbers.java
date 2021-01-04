package net.lc;
import net.lc.BinaryTreeBuilder.TreeNode;

/**
 * https://leetcode.com/problems/sum-root-to-leaf-numbers/submissions/
 * Binary Tree
 * DFS
 */
public class SumRootToLeafNumbers {
    private int sum = 0;

    public int sumNumbers(TreeNode root) {
        if (root == null) return sum;
        dfs(root, 0);
        return sum;
    }

    private void dfs(TreeNode cur, int number) {
        int updatedNumber = number * 10 + cur.val;

        if (cur.left == null && cur.right == null) {
            sum += updatedNumber;
            return;
        }

        if (cur.left != null) {
            dfs(cur.left, updatedNumber);
        }

        if (cur.right != null) {
            dfs(cur.right, updatedNumber);
        }
    }

    public static void main(String[] args) {
        TreeNode rootNode = BinaryTreeBuilder.buildTree("4,9,0,5,1");
        System.out.println(new SumRootToLeafNumbers().sumNumbers(rootNode));
    }
}
