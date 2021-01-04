package net.lc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 863
 * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/submissions/
 * Binary Tree
 */
public class AllNodesDistanceKBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    static class Pair {
        int direction;
        TreeNode node;

        public Pair(int direction, TreeNode node) {
            this.direction = direction;
            this.node = node;
        }
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        if (K == 0) return Collections.singletonList(target.val);

        List<Integer> output = new ArrayList<>();
        Stack<Pair> path = new Stack<>();
        findPath(root, target, path, 1);

        int distanceFromTarget = 0;
        Pair lastPopped = null;
        while (!path.isEmpty()) {

            Pair top = path.peek();
            if (distanceFromTarget == 0) {
                findNodesAtDistanceK(top.node, K, output);
            } else {
                if (distanceFromTarget == K) {
                    output.add(top.node.val);
                } else {
                    int dist = K - (distanceFromTarget + 1);
                    if (dist < 0) break;
                    TreeNode startNode = (lastPopped.direction == 0) ? top.node.right : top.node.left;
                    findNodesAtDistanceK(startNode, dist, output);
                }
            }
            lastPopped = path.pop();
            distanceFromTarget++;
        }

        return output;
    }

    private void findNodesAtDistanceK(TreeNode startNode, int K, List<Integer> nodes) {
        findNodesAtDistance(startNode, K, nodes, 0);
    }

    private void findNodesAtDistance(TreeNode curNode, int K, List<Integer> nodes, int curD) {
        if (curNode == null) return;
        if (curD == K) {
            nodes.add(curNode.val);
            return;
        }

        findNodesAtDistance(curNode.left, K, nodes, curD+1);
        findNodesAtDistance(curNode.right, K, nodes, curD+1);
    }

    private boolean findPath(TreeNode cur, TreeNode target, Stack<Pair> path, int direction) {
        if (cur == null) return false;
        path.push(new Pair(direction, cur));
        if (cur.val == target.val) {
            return true;
        }

        if (findPath(cur.left, target, path, 0)) return true;

        if (findPath(cur.right, target, path, 1)) return true;

        path.pop();
        return false;
    }
}
