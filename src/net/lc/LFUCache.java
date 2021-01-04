package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 460
 * LRU
 * O(1)
 */
public class LFUCache {
    static class Node {
        int key;
        Node next = null;
        Node prev = null;

        public Node(int key) {
            this.key = key;
        }
    }

    static class LRULane {
        private Node head = null;
        private Node tail = null;
        private final Map<Integer, Node> keyLocationMap = new HashMap<>();

        public LRULane() { }

        private boolean isEmpty() {
            return (head == null && tail == null);
        }

        public void add(Integer key) {
            Node n = new Node(key);

            if (isEmpty()) {
                head = n;
                tail = n;
                keyLocationMap.put(key, tail);
            } else {
                tail.next = n;
                n.prev = tail;
                tail = n;
                keyLocationMap.put(key, tail);
            }
        }

        public int removeHead() {
            if (isEmpty()) return -1;
            int key = head.key;
            keyLocationMap.remove(key);
            if (keyLocationMap.isEmpty()) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return key;
        }

        public void remove(Integer key) {
            if (isEmpty()) return;

            Node n = keyLocationMap.remove(key);
            if (keyLocationMap.isEmpty()) {
                head = null;
                tail = null;
                return;
            }

            if (n == tail) {
                tail = tail.prev;
                tail.next = null;
            } else if (n == head) {
                head = head.next;
                head.prev = null;
            } else {
                n.prev.next = n.next;
                n.next.prev = n.prev;
            }
        }
    }

    private final int capacity;
    private final Map<Integer, Integer> map = new HashMap<>();
    private final Map<Integer, Integer> frequencyMap = new HashMap<>();
    /**
     * LRU Cache of all keys of a particular Frequency.
     * Frequency is updated on a get() or a put()
     * Whenever frequency is updated, an item moves from one lane to another lane.
     * For eviction, we choose the LRU item from a LFU Lane.
     */
    private final Map<Integer, LRULane> frequencyLanes = new HashMap<>();

    private int leastFrequency = 0;
    private int currentCapacity = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        int curFrequency = frequencyMap.get(key);
        LRULane oldLane = frequencyLanes.get(curFrequency);

        oldLane.remove(key);
        if (oldLane.isEmpty()) {
            frequencyLanes.remove(curFrequency);
            if (leastFrequency == curFrequency) {
                leastFrequency = curFrequency + 1;
            }
        }
        curFrequency++;
        frequencyMap.replace(key, curFrequency);
        LRULane newLane = frequencyLanes.computeIfAbsent(curFrequency, k -> new LRULane());
        newLane.add(key);

        return map.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (!map.containsKey(key)) {
            if (currentCapacity == capacity) {
                LRULane lf = frequencyLanes.get(leastFrequency);
                int removedKey = lf.removeHead();
                if (lf.isEmpty()) {
                    frequencyLanes.remove(leastFrequency);
                }
                map.remove(removedKey);
                frequencyMap.remove(removedKey);
                //System.out.println("EVICTING: " + removedKey);
                currentCapacity--;
            }

            map.put(key, value);
            frequencyMap.put(key, 0);
            LRULane lf = frequencyLanes.computeIfAbsent(0, k -> new LRULane());
            leastFrequency = 0;
            lf.add(key);
            currentCapacity++;
        } else {
            int curFrequency = frequencyMap.get(key);
            LRULane oldLane = frequencyLanes.get(curFrequency);
            oldLane.remove(key);
            if (oldLane.isEmpty()) {
                frequencyLanes.remove(curFrequency);
                if (leastFrequency == curFrequency) {
                    leastFrequency = curFrequency + 1;
                }
            }
            curFrequency++;
            frequencyMap.replace(key, curFrequency);
            LRULane newLane = frequencyLanes.computeIfAbsent(curFrequency, k -> new LRULane());
            newLane.add(key);
            map.put(key, value);
        }
    }
}
