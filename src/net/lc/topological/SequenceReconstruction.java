package net.lc.topological;

import java.util.*;

/**
 * https://leetcode.com/problems/sequence-reconstruction/submissions/
 * 444
 * Topological Sort
 */
public class SequenceReconstruction {
    private final Map<Integer, Set<Integer>> successorMap = new HashMap<>();
    private final List<Integer> topoL = new ArrayList<>();

    private final Set<Integer> nodeSet = new HashSet<>();
    private int beginNode = 0;
    int nodecount = 0;

    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {

        for (List<Integer> seq : seqs) {
            if (!seq.isEmpty()) {
                nodeSet.addAll(seq);
            }
        }

        if (org.length > nodeSet.size()) return false;
        nodecount = nodeSet.size();

        Set<Integer> targetNodes = new HashSet<>();
        for (List<Integer> seq : seqs) {
            if (seq.isEmpty()) {
                continue;
            }

            for (int index = 0; index < seq.size()-1; index++) {
                int curNode = seq.get(index);
                int nextNode = seq.get(index+1);
                targetNodes.add(nextNode);

                Set<Integer> successors = successorMap.computeIfAbsent(curNode, k -> new HashSet<>());
                successors.add(nextNode);
            }
        }

        nodeSet.removeAll(targetNodes);

        if (nodeSet.size() != 1) {
            return false;
        }

        beginNode = nodeSet.iterator().next();

        if (hasCycleOrDisconnected()) {
            return false;
        }

        int[] topoArray = new int[topoL.size()];
        int index = topoArray.length-1;
        for (int val : topoL) {
            topoArray[index--] = val;
        }

        Map<Integer, Integer> distMap = new HashMap<>();
        for (int node : topoArray) distMap.put(node, Integer.MIN_VALUE);
        distMap.put(beginNode, 0);

        int maxD = 0;

        for (int node : topoArray) {
            Set<Integer> adjset = successorMap.get(node);

            if (adjset != null) {
                int curDistance = distMap.get(node);
                for (int adj : adjset) {
                    if (distMap.get(adj) < 1 + curDistance) {
                        distMap.put(adj, 1 + curDistance);
                        maxD = 1 + curDistance;
                    }
                }
            }
        }

        if (maxD < topoArray.length-1) {
            return false;
        }

        return Arrays.equals(topoArray, org);
    }

    public boolean hasCycleOrDisconnected() {
        final Set<Integer> visited = new HashSet<>();
        final Stack<Integer> path = new Stack<>();
        boolean cycleExists = hasCycle(beginNode, visited, path);

        if (cycleExists) return true;

        if (visited.size() < nodecount) return true;

        return false;
    }

    private boolean hasCycle(final int curNode, final Set<Integer> visited,
        final Stack<Integer> path) {

        //System.out.println("hasCycle: " + curNode);

        visited.add(curNode);
        path.push(curNode);
        if (successorMap.get(curNode) != null) {
            for (final int adj : successorMap.get(curNode)) {

                if (path.contains(adj))
                    return true;

                if (!visited.contains(adj)) {
                    if (hasCycle(adj, visited, path)) {
                        return true;
                    }
                }
            }
        }
        path.pop();
        topoL.add(curNode);
        return false;
    }
}
