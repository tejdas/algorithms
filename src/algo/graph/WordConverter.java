package algo.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordConverter {

    private static Set<String> words = new HashSet<>();
    public static void main(final String[] args) throws IOException {
        loadDictionary();

        //convertWord("live", "dead");
        //convertWord("ape", "man");
        convertWord("lead", "gold");
    }

    private static boolean isValidWord(final String word) {
        return words.contains(word);
    }

    private static void convertWord(final String from, final String to) {
        final Set<String> visited = new HashSet<>();

        final Map<String, String> edgeTo = new HashMap<>();

        visited.add(from);

        final Queue<String> nodes = new LinkedList<>();
        nodes.add(from);

        while (!nodes.isEmpty()) {
            final String curNode = nodes.remove();

            final char[] array = curNode.toCharArray();

            for (int i = 0; i < array.length; i++) {
                final char c = array[i];
                for (int j = 0; j < 26; j++) {
                    final char d = (char) ('a' + j);
                    if (c != d) {
                        array[i] = d;
                        final String word = new String(array);
                        if (word.equalsIgnoreCase(to)) {
                            System.out.println("found: " + to);
                            edgeTo.put(word,  curNode);

                            printPath(edgeTo, word);
                            return;
                        }

                        if (isValidWord(word)) {
                            if (!visited.contains(word)) {
                                visited.add(word);
                                nodes.add(word);
                                edgeTo.put(word, curNode);
                            }
                        }
                    }
                }
                array[i] = c;
            }
        }
    }

    /**
     * @param edgeTo
     * @param word
     */
    private static void printPath(final Map<String, String> edgeTo, final String word) {
        String temp = word;
        while (true) {
            final String fromTemp = edgeTo.get(temp);
            if (fromTemp == null) {
                break;
            }
            System.out.println(fromTemp);
            temp = fromTemp;
        }
    }

    private static void loadDictionary() throws IOException {
        final String fileName = "/usr/share/dict/web2";
        final BufferedReader freader = new BufferedReader(new FileReader(fileName));

        try {
            String sLine = null;
            while ((sLine = freader.readLine()) != null) {
                words.add(sLine);
            }
        } finally {
            freader.close();
        }
    }
}
