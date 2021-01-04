package algo.linkedlist;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LRUCache {

    static class Node {
        int key;
        Node next = null;

        public Node(int key) {
            this.key = key;
        }
    }
    private Node head = null;
    private Node tail = null;
    private final Map<Integer, Integer> map = new HashMap<>();
    private final int capacity;

    private final Map<Integer, Node> keyLocationMap = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    private boolean isEmpty() {
        return (head == null && tail == null);
    }

    public void put(int key, int value) {
        if (capacity == 1) {
            map.clear();
            map.put(key, value);
            return;
        }

        if (map.size() < capacity) {
            if (map.containsKey(key)) {
                map.put(key, value);
                get(key);
            } else {
                map.put(key, value);
                Node n = new Node(key);

                if (isEmpty()) {
                    head = n;
                    tail = n;
                    keyLocationMap.put(key, null);
                } else {
                    tail.next = n;
                    keyLocationMap.put(key, tail);
                    tail = n;
                    tail.next = null;
                }
            }
        } else {

            if (map.containsKey(key)) {
                map.put(key, value);
                get(key);
            } else {
                map.remove(head.key);
                head = head.next;
                keyLocationMap.put(head.key, null);

                Node n = new Node(key);
                map.put(key, value);
                tail.next = n;
                keyLocationMap.put(key, tail);
                tail = n;
                tail.next = null;
            }
        }
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        if (capacity == 1) {
            return map.get(key);
        }

        if (key == tail.key) {
            // do nothing
        } else if (key == head.key) {
            Node n = head;
            head = head.next;
            keyLocationMap.put(head.key, null);

            tail.next = n;
            keyLocationMap.put(key, tail);
            tail = n;
            tail.next = null;
        } else {
            Node prevNode = keyLocationMap.get(key);

            Node n = prevNode.next;
            prevNode.next = n.next;
            keyLocationMap.put(n.next.key, prevNode);

            tail.next = n;
            keyLocationMap.put(key, tail);
            tail = n;
            tail.next = null;
        }
        return map.get(key);
    }

    public static void main2(String[] args) {
        {
            LRUCache cache = new LRUCache(2 /* capacity */);

            cache.put(1, 1);
            cache.put(2, 2);
            System.out.println(cache.get(1));
            cache.put(3, 3);
            System.out.println(cache.get(2));
            cache.put(4, 4);
            System.out.println(cache.get(1));
            System.out.println(cache.get(3));
            System.out.println(cache.get(4));
            System.out.println("-----------");
        }

        {
            LRUCache cache = new LRUCache(2 /* capacity */);
            System.out.println(cache.get(2));
            cache.put(2, 6);
            System.out.println(cache.get(1));
            cache.put(1, 5);
            cache.put(1, 2);
            System.out.println(cache.get(1));
            System.out.println(cache.get(2));

        }
    }

    private void printLinkedList(String action) {
        if (!isEmpty()) {
            Node n = head;
            System.out.print(action + " --->");
            while (n != null) {
                System.out.print(n.key + " ");
                n = n.next;
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        String input = "[10,13]:[3,17]:[6,11]:[10,5]:[9,10]:[13]:[2,19]:[2]:[3]:[5,25]:[8]:[9,22]:[5,5]:[1,30]:[11]:[9,12]:[7]:[5]:[8]:[9]:[4,30]:[9,3]:[9]:[10]:[10]:[6,14]:[3,1]:[3]:[10,11]:[8]:[2,14]:[1]:[5]:[4]:[11,4]:[12,24]:[5,18]:[13]:[7,23]:[8]:[12]:[3,27]:[2,12]:[5]:[2,9]:[13,4]:[8,18]:[1,7]:[6]:[9,29]:[8,21]:[5]:[6,30]:[1,12]:[10]:[4,15]:[7,22]:[11,26]:[8,17]:[9,29]:[5]:[3,4]:[11,30]:[12]:[4,29]:[3]:[9]:[6]:[3,4]:[1]:[10]:[3,29]:[10,28]:[1,20]:[11,13]:[3]:[3,12]:[3,8]:[10,9]:[3,26]:[8]:[7]:[5]:[13,17]:[2,27]:[11,15]:[12]:[9,19]:[2,15]:[3,16]:[1]:[12,17]:[9,1]:[6,19]:[4]:[5]:[5]:[8,1]:[11,7]:[5,2]:[9,28]:[1]:[2,2]:[7,4]:[4,22]:[7,24]:[9,26]:[13,28]:[11,26]";

        String[] arr = input.split(":");
        final String VARIABLE_REGEX = "\\[(.*?)\\]";
        final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);

        LRUCache cache = new LRUCache(10);


        for (String s : arr) {
            Matcher matcher = VARIABLE_PATTERN.matcher(s);
            if (matcher.find()) {
                String str = matcher.group(1);
                String[] array = str.split(",");
                if (array.length == 2) {
                    int key = Integer.parseInt(array[0]);
                    int val = Integer.parseInt(array[1]);
                    cache.put(key, val);
                    //cache.printLinkedList("PUT");
                } else {
                    int key = Integer.parseInt(array[0]);
                    int val = cache.get(key);
                    //if (val == 24) {
                        //cache.printLinkedList("GET");
                    //}
                    System.out.println(key + "     " + val);
                }
            }
        }
    }
}
