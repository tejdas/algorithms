package net.lc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/all-oone-data-structure/
 */
public class AllOneDataStructure {
    static class Bucket {
        Bucket prev = null;
        Bucket next = null;
        Set<String> set = new HashSet<>();
        int count;

        public Bucket(int count) {
            this.count = count;
        }
    }

    private Bucket head = null;
    private Bucket tail = null;
    private Map<String, Integer> map = new HashMap<>();

    private Map<Integer, Bucket> locationMap = new HashMap<>();

    public void inc(String key) {
        if (!map.containsKey(key)) {
            map.put(key, 1);

            if (locationMap.isEmpty()) {
                Bucket bucket = new Bucket(1);
                locationMap.put(1, bucket);
                head = bucket;
                tail = bucket;
                bucket.set.add(key);
            } else {

                Bucket bucket = locationMap.get(1);
                if (bucket != null) {
                    bucket.set.add(key);
                } else {
                    bucket = new Bucket(1);
                    bucket.set.add(key);
                    bucket.next = head;
                    head.prev = bucket;
                    head = bucket;
                    locationMap.put(1, bucket);
                }
            }
        } else {
            int count = map.get(key);
            Bucket bOld = locationMap.get(count);
            bOld.set.remove(key);

            Bucket bNew = locationMap.get(count+1);

            if (bOld.set.isEmpty() && bNew == null) {
                bOld.count = count+1;
                bOld.set.add(key);
                locationMap.remove(count);
                locationMap.put(count+1, bOld);
            } else {
                if (bNew == null) {
                    bNew = new Bucket(count+1);
                    locationMap.put(count+1, bNew);
                    bNew.next = bOld.next;

                    if (bOld.next != null) {
                        bOld.next.prev = bNew;
                    }
                    bNew.prev = bOld;
                    bOld.next = bNew;
                    if (tail == bOld) tail = bNew;
                }

                bNew.set.add(key);

                if (bOld.set.isEmpty()) {
                    locationMap.remove(count);
                    if (bOld == head) {
                        head = bOld.next;
                        head.prev = null;
                    } else {
                        bOld.prev.next = bOld.next;
                        bOld.next.prev = bOld.prev;
                    }
                }
            }

            map.replace(key, count+1);
        }
    }

    public void dec(String key) {
        if (!map.containsKey(key)) return;

        int count = map.get(key);
        Bucket bOld = locationMap.get(count);
        bOld.set.remove(key);
        if (count == 1) {
            map.remove(key);
            if (map.isEmpty()) {
                locationMap.clear();
                head = null; tail = null;
                return;
            }

            if (bOld.set.isEmpty()) {
                locationMap.remove(count);
                if (bOld == head) {
                    head = bOld.next;
                    head.prev = null;
                } else {
                    bOld.prev.next = bOld.next;
                    if (bOld.next != null)
                        bOld.next.prev = bOld.prev;
                }
            }
        } else {
            Bucket bNew = locationMap.get(count-1);
            if (bOld.set.isEmpty() && bNew == null) {
                bOld.count = count-1;
                bOld.set.add(key);
                locationMap.remove(count);
                locationMap.put(count-1, bOld);
            } else {
                if (bNew == null) {
                    bNew = new Bucket(count-1);
                    locationMap.put(count-1, bNew);

                    bNew.prev = bOld.prev;
                    bNew.next = bOld;

                    if (bOld.prev != null) {
                        bOld.prev.next = bNew;
                    }
                    bOld.prev = bNew;
                    if (head == bOld) head = bNew;
                }

                bNew.set.add(key);

                if (bOld.set.isEmpty()) {
                    locationMap.remove(count);
                    if (bOld == tail) {
                        tail = bOld.prev;
                        tail.next = null;
                    } else {
                        bOld.prev.next = bOld.next;
                        if (bOld.next != null)
                            bOld.next.prev = bOld.prev;
                    }
                }
            }

            map.replace(key, count-1);
        }
    }

    public String getMaxKey() {
        if (map.isEmpty()) return "";
        return tail.set.stream().findAny().get();
    }

    public String getMinKey() {
        if (map.isEmpty()) return "";
        return head.set.stream().findAny().get();
    }
}
