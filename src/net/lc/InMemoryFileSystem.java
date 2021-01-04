package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/design-in-memory-file-system/submissions/
 * Trie
 */
public class InMemoryFileSystem {
    static class TrieNode {
        String name;
        boolean isDirectory = true;
        StringBuilder content = null;
        Map<String, TrieNode> children = new HashMap<>();

        public TrieNode(String name) {
            this.name = name;
            isDirectory = true;
        }

        public TrieNode(String name, String content) {
            isDirectory = false;
            this.name = name;
            this.content = new StringBuilder(content);
        }

        public void append(String morecontent) {
            content.append(morecontent);
        }
    }

    private final TrieNode root = new TrieNode("/");

    public InMemoryFileSystem() {
    }

    public List<String> ls(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        TrieNode cur = root;

        if (!path.isEmpty()) {

            String[] array = path.split("/");

            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    String token = array[i];
                    TrieNode child = cur.children.get(token);
                    if (child == null)
                        return null;
                    cur = child;
                }
            }
        }

        if (cur.isDirectory) {
            List<String> names = new ArrayList<>(cur.children.keySet());
            Collections.sort(names);
            return names;
        } else {
            return Collections.singletonList(cur.name);
        }
    }

    public void mkdir(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String[] array = path.split("/");
        mkdirInternal(array, 0, root);
    }

    private void mkdirInternal(String[] array, int pos, TrieNode cur) {
        if (pos == array.length) return;

        String curDir = array[pos];
        if (!cur.children.containsKey(curDir)) {
            cur.children.put(curDir, new TrieNode(curDir));
        }

        TrieNode child = cur.children.get(curDir);
        mkdirInternal(array, pos+1, child);
    }

    public void addContentToFile(String filePath, String content) {

        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }

        String[] array = filePath.split("/");

        TrieNode cur = root;
        for (int i = 0; i < array.length-1; i++) {
            String token = array[i];
            TrieNode child = cur.children.get(token);
            if (child == null) return;
            cur = child;
        }

        String fileName = array[array.length-1];
        if (!cur.children.containsKey(fileName)) {
            cur.children.put(fileName, new TrieNode(fileName, content));
        } else {
            TrieNode fNode = cur.children.get(fileName);
            fNode.append(content);
        }
    }

    public String readContentFromFile(String filePath) {
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }

        String[] array = filePath.split("/");

        TrieNode cur = root;
        for (int i = 0; i < array.length; i++) {
            String token = array[i];
            TrieNode child = cur.children.get(token);
            if (child == null) return null;
            cur = child;
        }

        if (cur.isDirectory) {
            return null;
        } else {
            return cur.content.toString();
        }
    }
}
