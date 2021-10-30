package net.lc.greedy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 968
 * https://leetcode.com/problems/binary-tree-cameras/submissions/
 * DFS post-order
 * Greedy
 */
public class BinaryTreeCamera {
    private static int gIndex = 0;
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    static class IndexedTreeNode{
        int index;
        IndexedTreeNode ileft;
        IndexedTreeNode iright;
        IndexedTreeNode() {
            index = gIndex++;
        }
    }

    private Set<Integer> coveredNodes = new HashSet<>();
    private Set<Integer> cameraLocation = new HashSet<>();

    public int minCameraCover(TreeNode root) {
        IndexedTreeNode iroot = buildTree(root);
        dfs(iroot, null);
        return cameraLocation.size();
    }

    private IndexedTreeNode buildTree(TreeNode cur) {
        if (cur == null) return null;
        IndexedTreeNode icur = new IndexedTreeNode();
        icur.ileft = buildTree(cur.left);
        icur.iright = buildTree(cur.right);
        return icur;
    }

    private void dfs(IndexedTreeNode cur, IndexedTreeNode par) {
        if (cur.ileft == null && cur.iright == null) {
            if (par == null) {
                cameraLocation.add(cur.index);
                coveredNodes.add(cur.index);
            }
            return;
        }

        if (cur.ileft != null) {
            dfs(cur.ileft, cur);
        }

        if (cur.iright != null) {
            dfs(cur.iright, cur);
        }

        boolean allchildrenCovered = true;
        boolean cameraAtChild = false;

        if (cur.ileft != null) {
            if (!coveredNodes.contains(cur.ileft.index)) {
                allchildrenCovered = false;
            }

            cameraAtChild = cameraAtChild || cameraLocation.contains(cur.ileft.index);
        }

        if (cur.iright != null) {
            if (!coveredNodes.contains(cur.iright.index)) {
                allchildrenCovered = false;
            }

            cameraAtChild = cameraAtChild || cameraLocation.contains(cur.iright.index);
        }

        if (!allchildrenCovered) {
            cameraLocation.add(cur.index);
            coveredNodes.add(cur.index);
            if (par != null)
                coveredNodes.add(par.index);
            if (cur.ileft != null)
                coveredNodes.add(cur.ileft.index);

            if (cur.iright != null)
                coveredNodes.add(cur.iright.index);
        } else {
            if (coveredNodes.contains(cur.index)) {
                return;
            }

            if (!cameraAtChild && par == null) {
                cameraLocation.add(cur.index);
                coveredNodes.add(cur.index);
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
