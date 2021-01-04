package net.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import net.lc.BinaryTreeBuilder.TreeNode;

public class BinaryTreeInorderTraversalNonRecurse {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        Stack<TreeNode> stack = new Stack<>();

        TreeNode curNode = root;

        while (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            result.add(curNode.val);

            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();
                result.add(curNode.val);
            }

            curNode = curNode.right;
        }

        return result;
    }
}
