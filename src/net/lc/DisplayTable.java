package net.lc;

import java.util.*;

/**
 * 1418
 */
public class DisplayTable {

    private final SortedSet<String> foodItems = new TreeSet<>();

    static class TableInfo {
        public TableInfo(int tableNumber) {
            this.tableNumber = tableNumber;
        }

        int tableNumber;
        Map<String, Integer> orderCountMap = new HashMap<>();

        public void addOrder(String item) {
            if (orderCountMap.containsKey(item)) {
                orderCountMap.put(item, 1 + orderCountMap.get(item));
            } else {
                orderCountMap.put(item, 1);
            }
        }

        public int getOrderCount(String item) {
            return orderCountMap.getOrDefault(item, 0);
        }
    }

    private final Map<Integer, TableInfo> tableInfoMap = new HashMap<>();

    private final List<List<String>> result = new ArrayList<>();
    public List<List<String>> displayTable(List<List<String>> orders) {

        for (List<String> order : orders) {
            int tableNumber = Integer.parseInt(order.get(1));
            String foodItem = order.get(2);
            foodItems.add(foodItem);

            TableInfo tableInfo = tableInfoMap.computeIfAbsent(tableNumber, k -> new TableInfo(tableNumber));
            tableInfo.addOrder(foodItem);
        }

        List<String> heading = new ArrayList<>();
        result.add(heading);
        heading.add("Table");

        String[] array = foodItems.toArray(new String[foodItems.size()]);

        for (String foodItem :  array) {
            heading.add(foodItem);
        }

        List<Integer> tableList = new ArrayList<>(tableInfoMap.keySet());
        Collections.sort(tableList);

        for (int table : tableList) {
            TableInfo tableInfo = tableInfoMap.get(table);
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(table));

            for (int i = 0;  i <  array.length; i++) {
                String foodItem = array[i];
                int count = tableInfo.getOrderCount(foodItem);
                row.add(String.valueOf(count));
            }
            result.add(row);
        }
        return result;
    }

    public static void main(String[] args) {
        /*
         [["David","3","Ceviche"],["Corina","10","Beef Burrito"],
         ["David","3","Fried Chicken"],["Carla","5","Water"],
         ["Carla","5","Ceviche"],["Rous","3","Ceviche"]]

         */
        List<String> l1 = Arrays.asList("David","3","Ceviche");
        List<String> l2 = Arrays.asList("Corina","10","Beef Burrito");
        List<String> l3 = Arrays.asList("David","3","Fried Chicken");
        List<String> l4 = Arrays.asList("Carla","5","Water");
        List<String> l5 = Arrays.asList("Carla","5","Ceviche");
        List<String> l6 = Arrays.asList("Rous","3","Ceviche");
        List<List<String>> input = Arrays.asList(l1, l2, l3, l4, l5, l6);
        List<List<String>> output =  new DisplayTable().displayTable(input);

        for (List<String>  o : output) {
            System.out.println(Arrays.toString(o.toArray(new String[o.size()])));
        }
    }
}
