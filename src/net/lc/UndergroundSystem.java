package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/design-underground-system/submissions/
 * Design
 */
public class UndergroundSystem {
    static class CustomerInfo {
        int id;
        String checkinStation;
        int checkinTime;

        public CustomerInfo(int id, String checkinStation, int checkinTime) {
            this.id = id;
            this.checkinStation = checkinStation;
            this.checkinTime = checkinTime;
        }
    }

    static class StationPair {
        String start;
        String end;

        public StationPair(String start, String end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            StationPair that = (StationPair) o;

            if (start != null ? !start.equals(that.start) : that.start != null)
                return false;
            return end != null ? end.equals(that.end) : that.end == null;
        }

        @Override
        public int hashCode() {
            int result = start != null ? start.hashCode() : 0;
            result = 31 * result + (end != null ? end.hashCode() : 0);
            return result;
        }
    }

    static class TripInfo {
        double totalTime;
        int tripCount;

        public TripInfo(int totalTime, int tripCount) {
            this.totalTime = totalTime;
            this.tripCount = tripCount;
        }
    }
    private final Map<Integer, CustomerInfo> customerInfoMap = new HashMap<>();
    private final Map<StationPair, TripInfo> avgTimeMap = new HashMap<>();
    public UndergroundSystem() {
    }

    public void checkIn(int id, String stationName, int t) {
        customerInfoMap.put(id, new CustomerInfo(id, stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        CustomerInfo customerInfo = customerInfoMap.remove(id);
        int duration = t - customerInfo.checkinTime;

        StationPair sp = new StationPair(customerInfo.checkinStation, stationName);
        TripInfo tripInfo = avgTimeMap.computeIfAbsent(sp, k -> new TripInfo(0, 0));
        tripInfo.tripCount++;
        tripInfo.totalTime += duration;
    }

    public double getAverageTime(String startStation, String endStation) {
        TripInfo tripInfo = avgTimeMap.get(new StationPair(startStation, endStation));
        if (tripInfo == null) return 0;
        return tripInfo.totalTime / tripInfo.tripCount;
    }

    public static void main(String[] args) {
        UndergroundSystem us = new UndergroundSystem();

        us.checkIn(10, "L", 3);
        us.checkOut(10, "P", 8);
        System.out.println(us.getAverageTime("L", "P"));

        us.checkIn(5, "L", 10);
        us.checkOut(5, "P", 16);
        System.out.println(us.getAverageTime("L", "P"));

        us.checkIn(2, "L", 21);
        us.checkOut(2, "P", 30);
        System.out.println(us.getAverageTime("L", "P"));



        /*
        ["UndergroundSystem","checkIn","checkOut","getAverageTime","checkIn","checkOut","getAverageTime","checkIn","checkOut","getAverageTime"]

[[],[10,"Leyton",3],[10,"Paradise",8],["Leyton","Paradise"],[5,"Leyton",10],[5,"Paradise",16],["Leyton","Paradise"],[2,"Leyton",21],[2,"Paradise",30],["Leyton","Paradise"]]
         */
    }
}
