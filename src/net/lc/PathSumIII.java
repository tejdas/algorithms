package net.lc;
import net.lc.BinaryTreeBuilder.*;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/path-sum-iii/submissions/
 * Binary Tree
 */
public class PathSumIII {
    private int targetSum;
    private int result = 0;
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        this.targetSum = targetSum;

        int[] array = new int[1024];
        Arrays.fill(array, 0);
        dfs(root, 0, 0, array);
        return result;
    }

    private void dfs(TreeNode curNode, int level, int sum, int[] array) {
        if (curNode == null) {
            return;
        }

        int newSum = sum+curNode.val;
        array[level] = newSum;
        if (newSum == targetSum) result++;
        if (level > 0 && curNode.val == targetSum) result++;

        for (int i = 0; i < level-1; i++) {
            if (newSum-array[i] == targetSum) result++;
        }

        dfs(curNode.left, level+1, newSum, array);
        dfs(curNode.right, level+1, newSum, array);
    }

    public static void main(String[] args) {
        {
            TreeNode root = BinaryTreeBuilder.buildTree("10,5,-3,3,2,null,11,3,-2,null,1");
            System.out.println(new PathSumIII().pathSum(root, 8));
        }

        {
            TreeNode root = BinaryTreeBuilder.buildTree("5,4,8,11,null,13,4,7,2,null,null,5,1");
            System.out.println(new PathSumIII().pathSum(root, 22));
        }

        {
            TreeNode root = BinaryTreeBuilder.buildTree("1,2");
            System.out.println(new PathSumIII().pathSum(root, 2));
        }
    }
}
