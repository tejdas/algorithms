package xxx.yyy;

public class KMPSearch {
    private String pattern;

    private int[][] dfa = null;

    void buildKMP(String pattern) {
        this.pattern = pattern;
        int patternLength = pattern.length();
        int R = 256; // Radix

        this.dfa = new int[R][patternLength];
        dfa[pattern.charAt(0)][0] = 1;

        int x = 0;
        for (int j = 1; j < patternLength; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];
            }
            dfa[pattern.charAt(j)][j] = j + 1;
            x = dfa[pattern.charAt(j)][x];
        }

        printDFA(dfa, patternLength);
    }

    static void printDFA(int[][] dfa, int patternLength) {
        int R = 256; // Radix
        for (int i = 0; i < R; i++) {
            boolean doPrint = false;
            for (int k1 = 0; k1 < patternLength; k1++) {
                if (dfa[i][k1] != 0) {
                    doPrint = true;
                    break;
                }
            }
            if (!doPrint)
                continue;

            System.out.print("printing for: " + (char) i);
            for (int k = 0; k < patternLength; k++)
                System.out.print("  " + dfa[i][k]);
            System.out.println();
        }
        System.out.println("--------------------");
    }

    public int search(String text) {
        int len = text.length();
        int patternLen = pattern.length();
        int i = 0, j = 0;
        for (; i < len && j < patternLen; i++) {
            j = dfa[text.charAt(i)][j];
        }
        if (j == patternLen) {
            return i - patternLen;
        } else {
            return len;
        }
    }

    public static void main(String[] args) {
        KMPSearch kmp = new KMPSearch();

        kmp.buildKMP("AACAA");
        // buildKMP("ABABAC");
        // buildKMP("AAAAAAAAA");
        // buildKMP("ABRACADABRA");

        int pos = kmp.search("AABRAACADABRAACAADABRA");
        System.out.println(pos);
    }
}
