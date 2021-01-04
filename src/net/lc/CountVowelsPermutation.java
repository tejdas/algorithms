package net.lc;

/**
 * 1220
 * Dynamic Programming
 * Bottom-up
 * Build Dependency Graph
 * a follows e, i, u
 * e follows a, i
 * etc
 */
public class CountVowelsPermutation {
    public int countVowelPermutation(int n) {
        if (n == 0) return 0;

        long[][] countEndingWithVowel = new long[n+1][5];
        for (int i = 0; i < 5; i++) {
            countEndingWithVowel[1][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            countEndingWithVowel[i][0] = countEndingWithVowel[i-1][1] % 1000000007 + countEndingWithVowel[i-1][2] % 1000000007 + countEndingWithVowel[i-1][4] % 1000000007;

            countEndingWithVowel[i][1] = countEndingWithVowel[i-1][0] % 1000000007 + countEndingWithVowel[i-1][2] % 1000000007;

            countEndingWithVowel[i][2] = countEndingWithVowel[i-1][1] % 1000000007 + countEndingWithVowel[i-1][3] % 1000000007;

            countEndingWithVowel[i][3] = countEndingWithVowel[i-1][2] % 1000000007;

            countEndingWithVowel[i][4] = countEndingWithVowel[i-1][2] % 1000000007 + countEndingWithVowel[i-1][3] % 1000000007;
        }

        long sum = 0;
        for (int i = 0; i < 5; i++) {
            sum = sum % 1000000007 +  countEndingWithVowel[n][i] % 1000000007;
        }
        return (int) sum % 1000000007;
    }
}
