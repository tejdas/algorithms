package net.lc.design;

import java.util.*;

/**
 * 1244
 * PriorityQueue
 */
public class Leaderboard {
    static class Entry implements Comparable<Entry> {
        int playerId;
        int score;

        public Entry(int playerId, int score) {
            this.playerId = playerId;
            this.score = score;
        }

        @Override
        public int compareTo(Entry o) {
            return Integer.compare(o.score, this.score);
        }
    }

    private final Map<Integer, Integer> map = new HashMap<>();
    private final PriorityQueue<Entry> pq = new PriorityQueue<>();

    public Leaderboard() {

    }

    public void addScore(int playerId, int score) {
        if (map.containsKey(playerId)) {
            map.put(playerId, score + map.get(playerId));
        } else {
            map.put(playerId, score);
        }

        if (!pq.isEmpty() && pq.peek().playerId == playerId) {
            pq.peek().score = map.get(playerId);
        } else {
            pq.add(new Entry(playerId, map.get(playerId)));
        }
    }

    public int top(int K) {

        int total = 0;
        int picked = 0;

        List<Entry> tobeAdded = new ArrayList<>();

        while (!pq.isEmpty()) {
            Entry entry = pq.remove();
            if (!map.containsKey(entry.playerId)) {
                continue;
            }

            if (entry.score == map.get(entry.playerId)) {
                total += entry.score;
                tobeAdded.add(entry);
                picked++;
                if (picked == K) break;
            }
        }

        tobeAdded.forEach( entry -> pq.add(entry));
        return total;
    }

    public void reset(int playerId) {
        map.remove(playerId);
    }

    public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard ();
        leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
        leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
        leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
        leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
        leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
        System.out.println(leaderboard.top(1));           // returns 73;
        leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
        leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
        leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
        System.out.println(leaderboard.top(3));           // returns 141 = 51 + 51 + 39;
    }
}
