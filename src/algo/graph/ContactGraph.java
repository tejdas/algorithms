package algo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactGraph {

    static class ContactNode {
        String node;
        Set<String> connectedNodes = new HashSet<>();
        Set<Integer> indices = new HashSet<>();

        public ContactNode(final String node, final int index) {
            this.node = node;
            this.indices.add(index);
        }

        public void addConnectedNodes(final String[] nodes) {
            connectedNodes.addAll(Arrays.asList(nodes));
        }

        public void addIndex(final int index) {
            indices.add(index);
        }
    }

    static final Map<String, ContactNode> contactMap = new HashMap<>();

    static void addContactInternal(final int index, final String val1, final String val2, final String val3) {

        final ContactNode node;
        if (contactMap.containsKey(val1)) {
            node = contactMap.get(val1);
            node.addIndex(index);
        } else {
            node = new ContactNode(val1, index);
            contactMap.put(val1, node);
        }

        node.addConnectedNodes(new String[] {val2, val3});
    }

    public static void addContact(final int index, final String val1, final String val2, final String val3) {
        addContactInternal(index, val1, val2, val3);
        addContactInternal(index, val2, val3, val1);
        addContactInternal(index, val3, val1, val2);
    }

    public static void findUniqueContacts() {
        final Set<String> visited = new HashSet<>();

        for (final String key : contactMap.keySet()) {
            if (!visited.contains(key)) {
                final Set<Integer> indices = new HashSet<>();
                dfs(contactMap.get(key), visited, indices);

                for (final int index : indices) System.out.print(index + "  ");
                System.out.println();
            }
        }
    }

    static void dfs(final ContactNode node, final Set<String> visited, final Set<Integer> indices) {
        if (visited.contains(node.node)) {
            return;
        }

        visited.add(node.node);
        indices.addAll(node.indices);
        for (final String adj : node.connectedNodes) {
            if (!visited.contains(adj)) {
                dfs(contactMap.get(adj), visited, indices);
            }
        }
    }

    public static void main1(final String[] args) {
        addContact(1, "abc", "def", "ghi");
        addContact(2, "jkl", "mno", "pqr");
        addContact(3, "mno", "xyz", "rst");
        addContact(4, "abc", "xyz", "tej");

        findUniqueContacts();
    }

    public static void main(final String[] args) {
        addContact(1, "a", "b", "c");
        addContact(2, "d", "e", "f");
        addContact(3, "h", "a", "e");
        addContact(4, "p", "q", "r");
        addContact(5, "s", "t", "u");
        addContact(6, "x", "y", "q");
        addContact(7, "z", "w", "s");


        findUniqueContacts();
    }
}
