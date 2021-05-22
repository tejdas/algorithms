package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 146
 * https://leetcode.com/problems/lru-cache/submissions/
 */
public class LRUCache {

    static class Node {
        int key;
        Node next = null;
        Node prev = null;

        public Node(int key) {
            this.key = key;
        }
    }

    private final Map<Integer, Integer> map = new HashMap<>();
    private final Map<Integer, Node> locationMap = new HashMap<>();
    private final int capacity;
    private int curCapacity = 0;

    private Node head = null;
    private Node tail = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node n = locationMap.get(key);

        if (n == tail) {
            return map.get(key);
        } else if (n == head) {
            head = head.next;
            head.prev = null;
        } else {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        tail.next = n;
        n.prev = tail;
        n.next = null;
        tail = n;

        return map.get(key);
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            Node n = locationMap.get(key);

            if (n == tail) return;

            if (n == head) {
                head = head.next;
                head.prev = null;
            } else {
                n.prev.next = n.next;
                n.next.prev = n.prev;
            }
            tail.next = n;
            n.prev = tail;
            n.next = null;
            tail = n;
        } else {
            map.put(key, value);
            Node n = new Node(key);
            locationMap.put(key, n);

            if (curCapacity == 0) {
                head = n;
                tail = n;
                curCapacity++;
            } else if (curCapacity < capacity) {
                tail.next = n;
                n.prev = tail;
                tail = n;
                curCapacity++;
            } else {
                map.remove(head.key);
                locationMap.remove(head.key);

                if (capacity == 1) {
                    head = n;
                    tail = n;
                } else {

                    head = head.next;
                    head.prev = null;

                    tail.next = n;
                    n.prev = tail;
                    tail = n;
                }
            }
        }
    }
}
