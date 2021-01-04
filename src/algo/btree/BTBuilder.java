package algo.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class BTBuilder {
    static class TreeInfo {
        public TreeInfo(final TreeNode node, final int index, final int left, final int right) {
            this.node = node;
            this.index = index;
            this.left = left;
            this.right = right;
        }

        TreeNode node = null;
        int index = -1;
        int left = -1;
        int right = -1;

        boolean hasLeft() { return left < index; }

        boolean hasRight() { return index < right; }
    }

    static TreeNode buildTreeFromInorderAndBFS(final List<Integer> inorderArray, final int[] bfsArray) {
        final Queue<TreeInfo> queue = new LinkedList<>();
        int bfsIndex = 0;
        int val = bfsArray[bfsIndex++];
        final TreeNode root = new TreeNode(val);
        final TreeInfo info = new TreeInfo(root, inorderArray.indexOf(val), 0, inorderArray.size()-1);
        if (info.hasLeft() || info.hasRight())
            queue.add(info);

        while (!queue.isEmpty()) {
            final TreeInfo treeInfo = queue.poll();
            if (treeInfo.hasLeft()) {
                val = bfsArray[bfsIndex++];
                final TreeInfo child = new TreeInfo(new TreeNode(val), inorderArray.indexOf(val), treeInfo.left, treeInfo.index-1);
                treeInfo.node.left = child.node;
                if (child.hasLeft() || child.hasRight())
                    queue.add(child);

            }
            if (treeInfo.hasRight()) {
                val = bfsArray[bfsIndex++];
                final TreeInfo child = new TreeInfo(new TreeNode(val), inorderArray.indexOf(val), treeInfo.index+1, treeInfo.right);
                treeInfo.node.right = child.node;
                if (child.hasLeft() || child.hasRight())
                    queue.add(child);
            }
        }
        return root;
    }

    static void traverseTreeVertically(final TreeNode root) {
        final SortedMap<Integer, List<TreeNode>> map = new TreeMap<>();
        traverseTreeVertically(root, 0, map);
        for (final int key : map.keySet()) {
            final List<TreeNode> list = map.get(key);
            System.out.print("Key: " + key + "  ");
            for (final TreeNode n : list) System.out.print(n.value + "  ");
            System.out.println();
        }
    }

    static void traverseTreeVertically(final TreeNode node, final int degree, final Map<Integer, List<TreeNode>> map) {
        List<TreeNode> list = map.get(degree);
        if (list == null) {
            list = new ArrayList<TreeNode>();
            map.put(degree,  list);
        }
        list.add(node);
        if (node.left != null) {
            traverseTreeVertically(node.left, degree-1, map);
        }
        if (node.right != null) {
            traverseTreeVertically(node.right, degree+1, map);
        }
    }

    static Stack<Integer> buildPath(final TreeNode root, final int val) {
        final Stack<Integer> path = new Stack<>();
        if (tracePath(root, val, path)) {
            for (final int i : path) System.out.print(i + "  ");
            System.out.println();
        }
        return path;
    }

    static int findDistance(final Stack<Integer> stackA, final Stack<Integer> stackB) {
        if (stackA.isEmpty() || stackB.isEmpty())
            return -1;

        final Iterator<Integer> iterA = stackA.iterator();
        final Iterator<Integer> iterB = stackB.iterator();

        int distance = 0;
        while (iterA.hasNext() && iterB.hasNext()) {
            final int a = iterA.next();
            final int b = iterB.next();
            if (a != b) {
                distance = 2;
                break;
            }
        }

        while (iterA.hasNext()) {
            iterA.next();
            distance++;
        }

        while (iterB.hasNext()) {
            iterB.next();
            distance++;
        }
        return distance;
    }

    static void findDistanceFromNode(final TreeNode root, final int val) {
        final Stack<Integer> sourcePath = buildPath(root, val);

        final Stack<Integer> targetPath = new Stack<>();

        findDistanceFromNode(root, targetPath, sourcePath);
    }

    static void findDistanceFromNode(final TreeNode node, final Stack<Integer> path, final Stack<Integer> sourcePath) {
        path.push(node.value);
        final int distance = findDistance(sourcePath, path);
        System.out.println("Distance of " + node.value + " is: " + distance);
        if (node.left != null) {
            findDistanceFromNode(node.left, path, sourcePath);
        }

        if (node.right != null) {
            findDistanceFromNode(node.right, path, sourcePath);
        }
        path.pop();
    }

    static boolean tracePath(final TreeNode node, final int val, final Stack<Integer> path) {
        path.push(node.value);
        if (node.value == val) {
            return true;
        }

        boolean found = false;
        if (node.left != null) {
            found = tracePath(node.left, val, path);
            if (found)
                return true;
        }
        if (node.right != null) {
            found = tracePath(node.right, val, path);
            if (found)
                return true;
        }
        path.pop();
        return false;
    }

    public static void main0(final String[] args) {
        final List<Integer> inorderArray = Arrays.asList(new Integer[] {8, 4, 2, 5, 9, 1, 6, 10, 3, 11, 7});
        final int[] bfsArray = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        final TreeNode root = buildTreeFromInorderAndBFS(inorderArray, bfsArray);
        root.traverseInOrder();
        System.out.println();
        root.traversePreOrder();
        System.out.println();
        TreeNode.traverseBFS(root);

        traverseTreeVertically(root);

        //buildPath(root, 15);

        findDistanceFromNode(root, 6);
    }

    public static void main(final String[] args) {
        final List<Integer> inorderArray = Arrays.asList(new Integer[] {8, 4, 2, 5, 9, 1, 6, 10, 3, 11, 7});
        final int[] bfsArray = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        final TreeNode root = buildTreeFromInorderAndBFS(inorderArray, bfsArray);
        root.traverseInOrder();
        System.out.println();
        TreeNode.traverseBFS(root);
    }

    public static void main2(final String[] args) {
        final Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        for (final int i : stack) {
            System.out.println(i);
        }
    }
}
