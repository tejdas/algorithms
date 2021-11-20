package net.lc.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 428
 * Queue
 * BFS
 */
public class SerdeNaryTree {
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb.toString();

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            sb.append(String.valueOf(cur.val)).append(' ');

            sb.append('[');
            if (cur.children != null) {
                sb.append(String.valueOf(cur.children.size()));

                if (cur.children.size() > 0) {
                    sb.append(':');
                }

                int count = 0;
                for (Node child : cur.children) {
                    if (count > 0) {
                        sb.append(',');
                    }
                    sb.append(String.valueOf(child.val));
                    queue.add(child);
                    count++;
                }
            } else {
                sb.append(Character.forDigit(0, 10));
            }
            sb.append("] ");
        }
        return sb.toString();
    }

    public Node deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] tokens = data.split(" ");

        int index = 0;
        Node root = new Node(Integer.valueOf(tokens[index]), new ArrayList<>());
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();

            if (index == tokens.length) break;

            String sval = tokens[index++];

            String s = tokens[index++];
            if (!s.equalsIgnoreCase("[0]")) {
                String ss = s.substring(1, s.length()-1);
                String[] sssplit = ss.split(":");

                int count = Integer.valueOf(sssplit[0]);
                String[] csplit = sssplit[1].split(",");

                for (String cval : csplit) {
                    Node child = new Node(Integer.valueOf(cval), new ArrayList<>());
                    cur.children.add(child);
                    queue.add(child);
                }
            }
        }
        return root;
    }
}
