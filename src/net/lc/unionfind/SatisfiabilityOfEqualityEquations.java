package net.lc.unionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/satisfiability-of-equality-equations/submissions/
 * Graph
 * Union-Find
 */
public class SatisfiabilityOfEqualityEquations {
    private final int[] parentMap = new int[26];

    public boolean equationsPossible(String[] equations) {
        if (equations == null || equations.length == 0) return true;

        Arrays.fill(parentMap, -1);

        Set<String> inequalExprSet = new HashSet<>();
        for (String equation : equations) {
            char c1 = equation.charAt(0);
            char c2 = equation.charAt(3);

            if (equation.charAt(1) == '!') {

                if (c1 == c2) return false;
                inequalExprSet.add(equation);
                continue;
            }

            if (c1 == c2) {
                int c1val = (int) c1 - 97;
                if (-1 == parentMap[c1val]) {
                    parentMap[c1val] = c1val;
                }
                continue;
            }

            int c1val = (int) c1 - 97;
            int c2val = (int) c2 - 97;
            if (-1 == parentMap[c1val]) {
                parentMap[c1val] = c1val;
            }

            if (-1 == parentMap[c2val]) {
                parentMap[c2val] = c2val;
            }

            union(c1val, c2val);
        }

        for (String s : inequalExprSet) {
            char c1 = s.charAt(0);
            char c2 = s.charAt(3);
            int c1val = (int) c1 - 97;
            int c2val = (int) c2 - 97;

            if (!isInequalityRespected(c1val, c2val)) {
                return false;
            }
        }
        return true;
    }

    private int findParent(int c) {
        int parent = parentMap[c];
        if (parent != c) {
            parent = findParent(parent);
            parentMap[c] = parent;
        }

        return parentMap[c];
    }

    public void union(int c1, int c2) {
        int p1 = findParent(c1);
        int p2 = findParent(c2);
        if (p1 != p2)
            parentMap[p1] = p2;
    }

    public boolean isInequalityRespected(int c1, int c2) {
        boolean hasParent1 = (-1 != parentMap[c1]);
        boolean hasParent2 = (-1 != parentMap[c2]);

        if (hasParent1 && hasParent2) {
            int p1 = findParent(c1);
            int p2 = findParent(c2);
            return (p1 != p2);
        } else {
            return true;
        }
    }
}
