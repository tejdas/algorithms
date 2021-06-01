package net.lc.greedy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/binary-tree-cameras/submissions/
 * DFS
 * Greedy
 */
public class BinaryTreeCamera {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private Set<TreeNode> coveredNodes = new HashSet<>();
    private Set<TreeNode> cameraLocation = new HashSet<>();

    public int minCameraCover(TreeNode root) {
        coveredNodes.add(null);
        dfs(root, null);
        return cameraLocation.size();
    }

    private void dfs(TreeNode cur, TreeNode par) {
        if (cur.left == null & cur.right == null) {
            if (par == null) {
                cameraLocation.add(cur);
                coveredNodes.add(cur);
            }
            return;
        }

        if (cur.left != null) {
            dfs(cur.left, cur);
        }

        if (cur.right != null) {
            dfs(cur.right, cur);
        }

        boolean allchildrenCovered = true;
        boolean cameraAtChild = false;

        if (cur.left != null) {
            if (!coveredNodes.contains(cur.left)) {
                allchildrenCovered = false;
            }

            cameraAtChild = cameraAtChild || cameraLocation.contains(cur.left);
        }

        if (cur.right != null) {
            if (!coveredNodes.contains(cur.right)) {
                allchildrenCovered = false;
            }

            cameraAtChild = cameraAtChild || cameraLocation.contains(cur.right);
        }

        if (!allchildrenCovered) {
            cameraLocation.add(cur);
            coveredNodes.add(cur);
            coveredNodes.add(par);
            coveredNodes.add(cur.left);
            coveredNodes.add(cur.right);
        } else {
            if (coveredNodes.contains(cur)) {
                return;
            }

            if (!cameraAtChild && par == null) {
                cameraLocation.add(cur);
                coveredNodes.add(cur);
            }
        }
    }

    static TreeNode NilNode = new TreeNode(-1);

    private static TreeNode buildTree(String input) {
        String[] array = input.split(",");
        if (array.length == 0) return null;
        Queue<TreeNode> queue = new LinkedList<>();

        int index = 0;
        int root = Integer.parseInt(array[index++]);
        TreeNode rootNode = new TreeNode(root);
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            TreeNode curNode = queue.remove();
            if (curNode.val == NilNode.val) continue;

            if (index < array.length) {
                String s = array[index++];
                if (s.equalsIgnoreCase("null")) {
                    queue.add(NilNode);
                } else {
                    curNode.left = new TreeNode(Integer.parseInt(s));
                    queue.add(curNode.left);
                }
            }

            if (index < array.length) {
                String s = array[index++];
                if (s.equalsIgnoreCase("null")) {
                    queue.add(NilNode);
                } else {
                    curNode.right = new TreeNode(Integer.parseInt(s));
                    queue.add(curNode.right);
                }
            }
        }
        return rootNode;
    }

    public static void main(String[] args) {

        {
            //1
            TreeNode rootNode = buildTree("0,0,null,0,0");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }

        {
            //2
            TreeNode rootNode = buildTree("0,0,null,0,null,0,null,null,0");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }

        {
            //2
            TreeNode rootNode = buildTree("0,0,0,null,0,null,0,null,null,0,null");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }

        {
            //2
            TreeNode rootNode = buildTree("0,0,0,null,null,null,0");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }


        {
            // 2
            TreeNode rootNode = buildTree("0,0,null,null,0,0,null,null,0,0");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }

        {
            // failing test   3
            TreeNode rootNode = buildTree("0,0,0,null,0,null,null,0,null,null,0,0");
            System.out.println(new BinaryTreeCamera().minCameraCover(rootNode));
        }
    }
}
