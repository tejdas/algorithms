package net.lc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeBasedKVStore {
    private final Map<String, TreeMap<Integer, String>> map = new HashMap<>();

    public TimeBasedKVStore() {
    }

    public void set(String key, String value, int timestamp) {
        if (!map.containsKey(key)) {
            map.put(key, new TreeMap<>(Collections.reverseOrder()));
        }

        map.get(key).put(timestamp, value);

    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }

        TreeMap<Integer, String> smap = map.get(key);

        Map.Entry<Integer, String> entry = smap.ceilingEntry(timestamp);
        if (entry == null)
            return "";

        return entry.getValue();
    }
}
