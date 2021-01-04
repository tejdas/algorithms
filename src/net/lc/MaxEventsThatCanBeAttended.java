package net.lc;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/
 * Greedy
 * All tests pass, but TLE, therefore, not accepted.
 */
public class MaxEventsThatCanBeAttended {
    static class Event implements Comparable<Event> {
        int index;
        int begin;
        int end;

        public Event(int index, int begin, int end) {
            this.index = index;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public int compareTo(Event o) {
            if (this.begin == o.begin) {
                return Integer.compare(this.end, o.end);
            }

            return Integer.compare(this.begin, o.begin);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Event{");
            sb.append("index=").append(index);
            sb.append(", begin=").append(begin);
            sb.append(", end=").append(end);
            sb.append('}');
            return sb.toString();
        }
    }
    public int maxEvents(int[][] events) {
        PriorityQueue<Event> pq = new PriorityQueue<>();

        System.out.println("total events: " + events.length);
        int index = 0;
        for (int[] e : events) {
            pq.add(new Event(index++, e[0], e[1]));
        }

        int numAttended = 0;

        int curDay = 1;

        int lastComputedIndex = -1;
        while (!pq.isEmpty()) {
            Event ev = pq.remove();
            if (ev.end < curDay) {
                System.out.println("Cannot attend event: " + ev);
                continue;
            } else if (ev.begin <= curDay) {

                if (ev.begin < curDay && ev.end > curDay && !pq.isEmpty()) {
                    // there might be another event that we might process sooner

                    ev.begin = curDay;
                    System.out.println("recomputed event for later processing: " + ev);
                    pq.add(ev);
                    lastComputedIndex = ev.index;
                } else {
                    System.out.println("attend event: on day: " + curDay + "     event: " + ev);
                    numAttended++;
                    curDay++;
                }
            } else {
                curDay = ev.begin;
                System.out.println("attend event: on day: " + curDay + "     event: " + ev);
                numAttended++;
                curDay++;
            }
        }
        return numAttended;
    }

    public static void main(String[] args) {
        /*
        {
            int[][] events = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }

        {
            int[][] events = { { 1, 2 }, { 2, 3 }, { 3, 4 }, {1,2} };
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }

        {
            int[][] events = { {1,4}, {4,4}, {2,2}, {3,4}, {1,1} };
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }

        {
            int[][] events = {{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7} };
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }

        {
            int[][] events = {{1,2},{1,2},{3,3},{1,5},{1,5} };
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }

        {
            int[][] events = {{26,27},{24,26},{1,4},{1,2},{3,6},{2,6},{9,13},{6,6},{25,27},{22,25},{20,24},{8,8},{27,27},{30,32}};
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }
        */

        {
            int[][] events = {{13,14},{29,32},{10,13},{10,11},{9,11},{21,25},{18,22},{30,30},{27,29},{13,17},{30,32},{17,17},{4,4},{20,23},{22,25},{9,11},{15,16},{24,26},{7,8},{15,19},{11,15},{5,8},{8,9},{23,26},{2,5},{27,28},{28,32}};
            int numEvents = new MaxEventsThatCanBeAttended().maxEvents(events);
            System.out.println(numEvents);
        }
    }
}
