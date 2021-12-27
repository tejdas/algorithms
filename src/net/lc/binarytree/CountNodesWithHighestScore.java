package net.lc.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * 2049
 * https://leetcode.com/problems/count-nodes-with-the-highest-score/submissions/
 */
public class CountNodesWithHighestScore {
    static class TreeNode {
        int id;
        TreeNode left;
        TreeNode right;
        int score;
        int leftCount;
        int rightCount;

        public TreeNode(int id) {
            this.id = id;
        }

        public void assign(TreeNode child) {
            if (left == null) {
                left = child;
            } else {
                right = child;
            }
        }
    }

    private Map<Long, Integer> scoreMap = new HashMap<>();
    private long highestScore = Long.MIN_VALUE;
    private TreeNode root = null;
    private int totalNodes;

    public int countHighestScoreNodes(int[] parents) {
        TreeNode[] array = new TreeNode[parents.length];
        for (int i = 0; i < parents.length; i++) {
            array[i] = new TreeNode(i);
        }

        totalNodes = parents.length;
        root = array[0];

        for (int i = 1; i < parents.length; i++) {
            TreeNode cur = array[i];
            int par = parents[i];
            TreeNode parentNode = array[par];
            parentNode.assign(cur);
        }

        postOrder(root);

        return scoreMap.get(highestScore);
    }

    private int postOrder(TreeNode cur) {
        if (cur == null) return 0;

        cur.leftCount = postOrder(cur.left);
        cur.rightCount = postOrder(cur.right);

        int nodeCount = 1 + cur.leftCount + cur.rightCount;

        if (cur.id == 0) {
            // root
            long score = computeFromLeftAndRight(cur);
        } else {
            // non root
            long score1  = (cur.leftCount > 0? cur.leftCount : 1) *  (cur.rightCount > 0? cur.rightCount : 1);

            long score2 = totalNodes - nodeCount;

            long score = score1 * score2;

            highestScore = Math.max(highestScore, score);
            if (scoreMap.containsKey(score)) {
                scoreMap.put(score, 1 + scoreMap.get(score));
            } else {
                scoreMap.put(score, 1);
            }
        }

        return nodeCount;
    }

    private long computeFromLeftAndRight(TreeNode cur) {
        long score = (cur.leftCount > 0? cur.leftCount : 1) *  (cur.rightCount > 0? cur.rightCount : 1);
        highestScore = Math.max(highestScore, score);
        if (scoreMap.containsKey(score)) {
            scoreMap.put(score, 1 + scoreMap.get(score));
        } else {
            scoreMap.put(score, 1);
        }
        return score;
    }

    public static void main(String[] args) {
        {
            int[] parents = { -1, 2, 0, 2, 0 };

           System.out.println(new CountNodesWithHighestScore().countHighestScoreNodes(parents));
            System.out.println("===========");
        }

        {
            int[] parents = { -1, 2, 0};

           System.out.println(new CountNodesWithHighestScore().countHighestScoreNodes(parents));
            System.out.println("===========");
        }

        {
            int[] parents = { -1, 0};

            System.out.println(new CountNodesWithHighestScore().countHighestScoreNodes(parents));
            System.out.println("===========");
        }
    }
}
